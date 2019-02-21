package de.thbingen.epro.project.servicebroker.helm;

import de.thbingen.epro.project.data.model.Operation;
import de.thbingen.epro.project.data.repository.OperationRepository;
import de.thbingen.epro.project.servicebroker.helm.exceptions.InstallFailedException;
import de.thbingen.epro.project.servicebroker.helm.exceptions.UninstallFailedException;
import hapi.chart.ChartOuterClass;
import hapi.release.ReleaseOuterClass;
import hapi.services.tiller.Tiller.InstallReleaseRequest;
import hapi.services.tiller.Tiller.InstallReleaseResponse;
import hapi.services.tiller.Tiller.UninstallReleaseRequest;
import hapi.services.tiller.Tiller.UninstallReleaseResponse;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.microbean.helm.ReleaseManager;
import org.microbean.helm.Tiller;
import org.microbean.helm.TillerInstaller;
import org.microbean.helm.chart.URLChartLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class HelmClient {
    @NonNull
    private OperationRepository operationRepository;

    private ExecutorService executorService = Executors.newCachedThreadPool();


    public void installTiller() {
        TillerInstaller tillerInstaller = new TillerInstaller();
        tillerInstaller.init();
    }

    public ChartBuilder loadChart(URL chartURL) throws IOException {
        ChartOuterClass.Chart.Builder chart = null;

        try (URLChartLoader chartLoader = new URLChartLoader()) {
            chart = chartLoader.load(chartURL);
        }

        return new ChartBuilder(chart);
    }


    public Release installChart(URL chartURL, String instanceId) throws IOException, InstallFailedException {
        return installChart(chartURL, instanceId, 300L);
    }

    public Release installChart(URL chartURL, String instanceId, long timeout) throws IOException, InstallFailedException {
        ChartBuilder chart = loadChart(chartURL);

        return installChart(chart, instanceId, timeout);
    }


    public Release installChart(ChartBuilder chart, String instanceId) throws IOException, InstallFailedException {
        return installChart(chart, instanceId, 300L);
    }

    public Release installChart(ChartBuilder chart, String instanceId, long timeout) throws IOException, InstallFailedException {
        return installChart(chart, instanceId, timeout, new ChartConfig());
    }

    public Release installChart(ChartBuilder chart, String instanceId, long timeout, ChartConfig chartConfig) throws IOException, InstallFailedException {
        try (DefaultKubernetesClient kubernetesClient = new DefaultKubernetesClient();
             Tiller tiller = new Tiller(kubernetesClient);
             ReleaseManager releaseManager = new ReleaseManager(tiller)) {
            InstallReleaseRequest.Builder requestBuilder = InstallReleaseRequest.newBuilder();

            requestBuilder.setTimeout(timeout);
            requestBuilder.setName(instanceId);
            requestBuilder.setWait(true);

            if(!chartConfig.isEmpty()){
                log.debug("ChartConfig provided");
                chart.getChartConfig().mergeFrom(chartConfig);
            }

            log.debug("Run install for: " + instanceId + " (timeout: " + timeout + ")");
            Future<InstallReleaseResponse> installResponseFuture = releaseManager.install(requestBuilder, chart.getChartBuilder());

            InstallReleaseResponse installResponse = installResponseFuture.get();   //Throws InterruptedException and ExecutionException
            log.debug("Installation completed");
            Release release = new Release(installResponse.getRelease());
            return release;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw new InstallFailedException("Execute installation failed (maybe instanceId (name) already exists?)", e);
        }

        throw new InstallFailedException("Unknown reason");
    }

    public Release uninstallChart(String instanceId) throws IOException, UninstallFailedException {
        return uninstallChart(instanceId, 300);
    }

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

    public void installChartAsync(ChartBuilder chart, String instanceId, long timeout, Operation operation) {
        operation.setMessage("Installation in progress");
        operationRepository.save(operation);

        installChartAsync(chart, instanceId, timeout, defaultSuccessHandler(operation));
    }

    public void installChartAsync(ChartBuilder chart, String instanceId, long timeout, AsyncTask.AfterTaskRunnable afterTask) {
        installChartAsync(chart, instanceId, timeout, new ChartConfig(), afterTask);
    }

    public void installChartAsync(ChartBuilder chart, String instanceId, long timeout, ChartConfig chartConfig, AsyncTask.AfterTaskRunnable afterTask)  {
        AsyncTask asyncTask = new AsyncTask(() -> installChart(chart, instanceId, timeout, chartConfig).isInitialized(), afterTask);
        Future<?> submit = executorService.submit(asyncTask);
        log.debug("Started async installation");
    }

    public void uninstallChartAsync(String instanceId, long timeout, Operation operation){
        operation.setMessage("Installation in progress");
        operationRepository.save(operation);
        uninstallChartAsync(instanceId, timeout, defaultSuccessHandler(operation));
    }

    public void uninstallChartAsync(String instanceId, long timeout, AsyncTask.AfterTaskRunnable afterTask){
        AsyncTask asyncTask = new AsyncTask(() -> uninstallChart(instanceId, timeout).hasDeleted(), afterTask);
        Future<?> submit = executorService.submit(asyncTask);
        log.debug("Started async uninstallation");
    }

    private AsyncTask.AfterTaskRunnable defaultSuccessHandler(Operation operation){
        return (success, exception) -> {
            log.info("Operation " + operation.getId() + " successfully: " + success);
            if(success){
                operation.setState(Operation.OperationState.SUCCEEDED);
            } else {
                operation.setState(Operation.OperationState.FAILED);
                operation.setMessage("(Un)Installation failed");
            }
            operationRepository.save(operation);
        };
    }

//    public Release uninstallChart(String instanceId, long timeout) throws IOException, UninstallFailedException {
//        return uninstallChart(instanceId, timeout);
//    }

    @RequiredArgsConstructor
    public static class AsyncTask implements Runnable{
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

        @FunctionalInterface
        public static interface AfterTaskRunnable{
            void afterTask(boolean success, Exception exception);
        }
    }
}
