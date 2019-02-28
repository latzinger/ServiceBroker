package de.thbingen.epro.project.servicebroker.helm;

import de.thbingen.epro.project.data.model.Operation;
import de.thbingen.epro.project.data.model.ServiceInstance;
import de.thbingen.epro.project.data.repository.OperationRepository;
import de.thbingen.epro.project.data.repository.ServiceInstanceRepository;
import de.thbingen.epro.project.servicebroker.helm.exceptions.InstallFailedException;
import de.thbingen.epro.project.servicebroker.helm.exceptions.UninstallFailedException;
import de.thbingen.epro.project.web.exception.ServiceInstanceNotFoundException;
import hapi.chart.ChartOuterClass;
import hapi.release.ReleaseOuterClass;
import hapi.services.tiller.Tiller.InstallReleaseRequest;
import hapi.services.tiller.Tiller.InstallReleaseResponse;
import hapi.services.tiller.Tiller.UninstallReleaseRequest;
import hapi.services.tiller.Tiller.UninstallReleaseResponse;
import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.microbean.helm.ReleaseManager;
import org.microbean.helm.Tiller;
import org.microbean.helm.TillerInstaller;
import org.microbean.helm.chart.URLChartLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * Implementation of MicroBean Helm API.
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class HelmClient {
    @Autowired
    @Lazy
    private OperationRepository operationRepository;

    @Autowired
    @Lazy
    private ServiceInstanceRepository serviceInstanceRepository;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    private Object waitLock = new Object();


    /**
     * Installs tiller if not already installed
     */
    public void installTiller() {
        TillerInstaller tillerInstaller = new TillerInstaller();
        tillerInstaller.init();
    }

    /**
     * Loads a Chart from URL and returns it as {@link ChartConfig}
     * @param chartURL
     * @return the helm chart
     * @throws IOException
     */

    public ChartBuilder loadChart(URL chartURL) throws IOException {
        ChartOuterClass.Chart.Builder chart = null;

        try (URLChartLoader chartLoader = new URLChartLoader()) {
            chart = chartLoader.load(chartURL);
        }

        return new ChartBuilder(chart);
    }

    /**
     * Gets the {@link ServiceDetails} from a service via serviceName
     * @param serviceName
     * @return
     * @throws ServiceInstanceNotFoundException
     */
    public ServiceDetails getServiceDetails(String serviceName) throws ServiceInstanceNotFoundException {


        try (DefaultKubernetesClient kubernetesClient = new DefaultKubernetesClient()) {
            io.fabric8.kubernetes.api.model.Service service = kubernetesClient.services().inNamespace("default").withName(serviceName).get();

            if (service != null)
                return new ServiceDetails(service);
        }

        return null;
    }

    /**
     * Gets the host address the helm client connects to
     * @return
     */

    public String getHost() {

        try (DefaultKubernetesClient kubernetesClient = new DefaultKubernetesClient()) {
            return kubernetesClient.getMasterUrl().getHost();
        }

    }

    /**
     * Gets a {@link Credentials} object for given secretName
     * @param secretName
     * @return
     */
    public Credentials getCredentials(String secretName) {


        try (DefaultKubernetesClient kubernetesClient = new DefaultKubernetesClient()) {
            Secret secret = kubernetesClient.secrets().inNamespace("default").withName(secretName).get();

            if (secret != null) {
                return new Credentials(secret);
            }
        }

        return null;
    }

    /**
     * Installs chart synchronous
     * @param chartURL
     * @param instanceId
     * @return
     * @throws IOException
     * @throws InstallFailedException
     */
    public Release installChart(URL chartURL, String instanceId) throws IOException, InstallFailedException {
        return installChart(chartURL, instanceId, 300L);
    }

    /**
     * Installs chart synchronous
     * @param chartURL
     * @param instanceId
     * @param timeout
     * @return
     * @throws IOException
     * @throws InstallFailedException
     */
    public Release installChart(URL chartURL, String instanceId, long timeout) throws IOException, InstallFailedException {
        ChartBuilder chart = loadChart(chartURL);

        return installChart(chart, instanceId, timeout);
    }


    /**
     * Installs chart synchronous
     * @param chart
     * @param instanceId
     * @return
     * @throws IOException
     * @throws InstallFailedException
     */
    public Release installChart(ChartBuilder chart, String instanceId) throws IOException, InstallFailedException {
        return installChart(chart, instanceId, 300L);
    }

    /**
     * Installs chart synchronous
     * @param chart
     * @param instanceId
     * @param timeout
     * @return
     * @throws IOException
     * @throws InstallFailedException
     */
    public Release installChart(ChartBuilder chart, String instanceId, long timeout) throws IOException, InstallFailedException {
        return installChart(chart, instanceId, timeout, new ChartConfig());
    }

    /**
     * Installs chart synchronous.
     * @param chart chart to install
     * @param instanceId helm instanceId
     * @param timeout installation timeout
     * @param chartConfig override {@link ChartConfig} in {@link ChartBuilder}
     * @return
     * @throws IOException
     * @throws InstallFailedException
     */
    public Release installChart(ChartBuilder chart, String instanceId, long timeout, ChartConfig chartConfig) throws IOException, InstallFailedException {
        try (DefaultKubernetesClient kubernetesClient = new DefaultKubernetesClient();
             Tiller tiller = new Tiller(kubernetesClient);
             ReleaseManager releaseManager = new ReleaseManager(tiller)) {
            InstallReleaseRequest.Builder requestBuilder = InstallReleaseRequest.newBuilder();

            requestBuilder.setTimeout(timeout);
            requestBuilder.setName(instanceId);
            requestBuilder.setWait(true);

            if (!chartConfig.isEmpty()) {
                log.debug("ChartConfig provided");
                chart.getChartConfig().mergeFrom(chartConfig);
            }

            log.debug("Run install for: " + instanceId + " (timeout: " + timeout + ")");
            Future<InstallReleaseResponse> installResponseFuture = releaseManager.install(requestBuilder, chart.getChartBuilder());

            InstallReleaseResponse installResponse = installResponseFuture.get();   //Throws InterruptedException and ExecutionException
            log.debug("Installation completed");

//            synchronized (waitLock) {
//                waitLock.notifyAll();
//            }

            Release release = new Release(installResponse.getRelease());
            return release;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            log.debug("Execute Install failed", e);
            throw new InstallFailedException("Execute installation failed (maybe instanceId (name) already exists?)", e);
        }


        throw new InstallFailedException("Unknown reason");
    }

    /**
     * Uninstalls chart synchronous
     * @param instanceId
     * @return
     * @throws IOException
     * @throws UninstallFailedException
     */
    public Release uninstallChart(String instanceId) throws IOException, UninstallFailedException {
        return uninstallChart(instanceId, 300);
    }

    /**
     * Uninstalls chart synchronous
     * @param instanceId helm instanceId
     * @param timeout uninstall timeout
     * @return
     * @throws IOException
     * @throws UninstallFailedException
     */
    public Release uninstallChart(String instanceId, long timeout) throws IOException, UninstallFailedException {

        try (DefaultKubernetesClient kubernetesClient = new DefaultKubernetesClient();
             Tiller tiller = new Tiller(kubernetesClient);
             ReleaseManager releaseManager = new ReleaseManager(tiller)) {

            UninstallReleaseRequest.Builder requestBuilder = UninstallReleaseRequest.newBuilder();
            requestBuilder.setTimeout(timeout);
            requestBuilder.setPurge(true);
            requestBuilder.setName(instanceId);

            Future<UninstallReleaseResponse> uninstallResponseFuture = releaseManager.uninstall(requestBuilder.build());
            ReleaseOuterClass.Release release = uninstallResponseFuture.get().getRelease();
            Release releaseRet = new Release(release);
            return releaseRet;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw new UninstallFailedException("Execute uninstall failed (maybe instance doesn't exist?)", e);
        }

        throw new UninstallFailedException("Unknown reason");
    }

    //ASYNC

    /**
     * Fetches credentials asynchronous
     * Calls the {@link Consumer} credentialsConsumer after fetching and afterTask afterwards
     * @param secretName
     * @param credentialsConsumer
     * @param afterTask
     */
    public void getCredentialsAsync(String secretName, Consumer<Credentials> credentialsConsumer, AsyncTask.AfterTaskRunnable afterTask){
        AsyncTask asyncTask = new AsyncTask(() -> {
            credentialsConsumer.accept(getCredentials(secretName));
            return true;
        }, afterTask);
        executorService.submit(asyncTask);
    }

    /**
     * Fetches credentials asynchronous
     * Calls the {@link Consumer} credentialsConsumer after fetching and saves the success to given {@link Operation}
     * @param secretName
     * @param credentialsConsumer
     * @param operation
     */
    public void getCredentialsAsync(String secretName, Consumer<Credentials> credentialsConsumer, Operation operation){
        getCredentialsAsync(secretName, credentialsConsumer, defaultBindingSuccessHandler(operation));
    }

    /**
     * Fetches {@link ServiceDetails} asynchronous
     * Calls the {@link Consumer} detailsConsumer after fetching and afterTask afterwards
     * @param serviceName
     * @param detailsConsumer
     * @param afterTask
     */
    public void getServiceDetailsAsync(String serviceName, Consumer<ServiceDetails> detailsConsumer, AsyncTask.AfterTaskRunnable afterTask){
        AsyncTask asyncTask = new AsyncTask(() -> {
            detailsConsumer.accept(getServiceDetails(serviceName));
            return true;
        }, afterTask);
        executorService.submit(asyncTask);
    }

    /**
     * Fetches {@link ServiceDetails} asynchronous
     * @param serviceName
     * @param detailsConsumer
     * @param operation
     */
    public void getServiceDetailsAsync(String serviceName, Consumer<ServiceDetails> detailsConsumer, Operation operation){
        getServiceDetailsAsync(serviceName, detailsConsumer, defaultBindingSuccessHandler(operation));
    }


    /**
     * Asynchronously installs a {@link ChartBuilder} saving success state to Operation
     * @param chart
     * @param instanceId
     * @param timeout
     * @param operation
     */
    public void installChartAsync(ChartBuilder chart, String instanceId, long timeout, Operation operation) {
        operation.setMessage("Installation in progress");
        operationRepository.save(operation);

        installChartAsync(chart, instanceId, timeout, defaultInstallationSuccessHandler(operation));
    }

    /**
     * Asynchronously installs a {@link ChartBuilder} and calling the {@link AsyncTask.AfterTaskRunnable} afterwards
     * @param chart
     * @param instanceId
     * @param timeout
     * @param afterTask
     */

    public void installChartAsync(ChartBuilder chart, String instanceId, long timeout, AsyncTask.AfterTaskRunnable afterTask) {
        installChartAsync(chart, instanceId, timeout, new ChartConfig(), afterTask);
    }

    /**
     * Asynchronously installs a {@link ChartBuilder} and calling the {@link AsyncTask.AfterTaskRunnable} afterwards
     * @param chart
     * @param instanceId
     * @param timeout
     * @param chartConfig
     * @param afterTask
     */
    public void installChartAsync(ChartBuilder chart, String instanceId, long timeout, ChartConfig chartConfig, AsyncTask.AfterTaskRunnable afterTask) {
        AsyncTask asyncTask = new AsyncTask(() ->{
            return installChart(chart, instanceId, timeout, chartConfig).isInitialized();
        }, afterTask);
        Future<?> submit = executorService.submit(asyncTask);
        log.debug("Started async installation");
    }

    /**
     * Asynchronously uninstalls a {@link ChartBuilder} and saving the success to given {@link Operation}
     * @param instanceId
     * @param timeout
     * @param operation
     */
    public void uninstallChartAsync(String instanceId, long timeout, Operation operation) {
        operation.setMessage("Uninstallation in progress");
        operationRepository.save(operation);
        uninstallChartAsync(instanceId, timeout, defaultUninstallationSuccessHandler(operation));
    }

    /**
     * Asynchronously uninstalls a {@link ChartBuilder} and calling the {@link AsyncTask.AfterTaskRunnable} afterwards
     * @param instanceId
     * @param timeout
     * @param afterTask
     */
    public void uninstallChartAsync(String instanceId, long timeout, AsyncTask.AfterTaskRunnable afterTask) {
        AsyncTask asyncTask = new AsyncTask(() -> {
            try {
                waitForInstanceReady(instanceId);
            } catch (Exception e){
                e.printStackTrace();
                log.error("===exception" ,e);
            }
            return uninstallChart(instanceId, timeout).hasDeleted();
        }, afterTask);
        Future<?> submit = executorService.submit(asyncTask);
        log.debug("Started async uninstallation");
    }

    /**
     * Default install {@link AsyncTask.AfterTaskRunnable} to save the success to an operation
     * @param operation
     * @return
     */
    private AsyncTask.AfterTaskRunnable defaultInstallationSuccessHandler(Operation operation) {
        return (success, exception) -> {
            log.info("Install Operation " + operation.getId() + " successfully: " + success + " exception: " + exception);
            if (success) {
                operation.setState(Operation.OperationState.SUCCEEDED);
                operation.setMessage("Installation  succeeded");
                ServiceInstance serviceInstanceById = serviceInstanceRepository.getServiceInstanceById(operation.getServiceInstance().getId());
                serviceInstanceById.setInitialized(true);

                synchronized (waitLock){
                    waitLock.notifyAll();
                }

                serviceInstanceRepository.save(serviceInstanceById);

            } else {
                operation.setState(Operation.OperationState.FAILED);
                operation.setMessage("Installation failed");
            }
            operationRepository.save(operation);
        };
    }

    /**
     * Default uninstall {@link AsyncTask.AfterTaskRunnable} to save the success to an operation
     * @param operation
     * @return
     */
    private AsyncTask.AfterTaskRunnable defaultUninstallationSuccessHandler(Operation operation) {
        return (success, exception) -> {
            log.info("Uninstall Operation " + operation.getId() + " successfully: " + success);
            if (success) {
                ServiceInstance serviceInstance = operation.getServiceInstance();
                serviceInstanceRepository.delete(serviceInstance);
            } else {
                operation.setState(Operation.OperationState.FAILED);
                operation.setMessage("Uninstalling failed");
                operationRepository.save(operation);
            }

        };
    }

    /**
     * Default binding {@link AsyncTask.AfterTaskRunnable} to save the success to an operation
     * @param operation
     * @return
     */
    private AsyncTask.AfterTaskRunnable defaultBindingSuccessHandler(Operation operation) {
        return (success, exception) -> {
            log.info("Binding Operation " + operation.getId() + " successfully: " + success);
            if (success) {
                operation.setState(Operation.OperationState.SUCCEEDED);
                operation.setMessage("Binding  succeeded");
            } else {
                operation.setState(Operation.OperationState.FAILED);
                operation.setMessage("Binding failed");
            }
            operationRepository.save(operation);
        };
    }

    /**
     * Waits until {@link ServiceInstance#isInitialized()} return true or timeout after 500 seconds
     * @param instanceId
     */

    public void waitForInstanceReady(String instanceId){
        ServiceInstance instance = serviceInstanceRepository.getServiceInstanceById(instanceId);
        synchronized (waitLock){
            Long start = System.currentTimeMillis();
            while (!instance.isInitialized() && (System.currentTimeMillis() - start < 500000)){
//                log.debug("Instance initialized: " + instance.isInitialized());
                try {
                    waitLock.wait(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                instance = serviceInstanceRepository.getServiceInstanceById(instanceId);
            }
            if(!instance.isInitialized()){
                throw new RuntimeException("waitForInstanceReady timeout");
            }
        }
    }

//    public Release uninstallChart(String instanceId, long timeout) throws IOException, UninstallFailedException {
//        return uninstallChart(instanceId, timeout);
//    }

    /**
     * Util class used for asynchronous tasks
     * First runs the {@link Callable} and afterwards run {@link AfterTaskRunnable} with its success or/and an exception thrown before if something goes wrong
     * Call afterTask again with success = false and an {@link Exception} if executing afterTask fails
     */
    @RequiredArgsConstructor
    public static class AsyncTask implements Runnable {
        @NonNull
        private Callable<Boolean> task;
        @NonNull
        private AsyncTask.AfterTaskRunnable afterTask;

        @Override
        public void run() {
            try {
                Boolean call = task.call();
                afterTask.afterTask(call, null);
            } catch (Exception e) {
                afterTask.afterTask(false, e);
            }
        }


        /**
         * {@link FunctionalInterface} for handle operation success and failure
         * if success = true an {@link Exception} IS NOT present
         * if success = false an {@link Exception} MAY be present
         */
        @FunctionalInterface
        public static interface AfterTaskRunnable {
            void afterTask(boolean success, Exception exception);
        }
    }
}
