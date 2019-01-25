package de.thbingen.epro.project.servicebroker.helm;

import de.thbingen.epro.project.servicebroker.helm.exceptions.InstallFailedException;
import de.thbingen.epro.project.servicebroker.helm.exceptions.UninstallFailedException;
import hapi.chart.ChartOuterClass;
import hapi.release.ReleaseOuterClass;
import hapi.services.tiller.Tiller.InstallReleaseRequest;
import hapi.services.tiller.Tiller.InstallReleaseResponse;
import hapi.services.tiller.Tiller.UninstallReleaseRequest;
import hapi.services.tiller.Tiller.UninstallReleaseResponse;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import org.microbean.helm.ReleaseManager;
import org.microbean.helm.Tiller;
import org.microbean.helm.TillerInstaller;
import org.microbean.helm.chart.URLChartLoader;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class HelmClient {


    public void installTiller() {
        TillerInstaller tillerInstaller = new TillerInstaller();
        tillerInstaller.init();
    }

    public ChartOuterClass.Chart.Builder loadChart(URL chartURL) throws IOException {
        ChartOuterClass.Chart.Builder chart = null;

        try (URLChartLoader chartLoader = new URLChartLoader()) {
            chart = chartLoader.load(chartURL);
        }

        return chart;
    }


    public ReleaseOuterClass.Release installChart(URL chartURL, String instanceId) throws IOException, InstallFailedException {
        return installChart(chartURL, instanceId, 300L);
    }

    public ReleaseOuterClass.Release installChart(URL chartURL, String instanceId, long timeout) throws IOException, InstallFailedException {
        ChartOuterClass.Chart.Builder chart = loadChart(chartURL);

        return installChart(chart, instanceId, timeout);
    }


    public ReleaseOuterClass.Release installChart(ChartOuterClass.Chart.Builder chart, String instanceId) throws IOException, InstallFailedException {
        return installChart(chart, instanceId, 300L);
    }

    @NotNull
    public ReleaseOuterClass.Release installChart(ChartOuterClass.Chart.Builder chart, String instanceId, long timeout) throws IOException, InstallFailedException {

        try (DefaultKubernetesClient kubernetesClient = new DefaultKubernetesClient();
             Tiller tiller = new Tiller(kubernetesClient);
             ReleaseManager releaseManager = new ReleaseManager(tiller)) {
            InstallReleaseRequest.Builder requestBuilder = InstallReleaseRequest.newBuilder();

            requestBuilder.setTimeout(timeout);
            requestBuilder.setName(instanceId);
            requestBuilder.setWait(true);

            Future<InstallReleaseResponse> installResponseFuture = releaseManager.install(requestBuilder, chart);

            InstallReleaseResponse installResponse = installResponseFuture.get();   //Throws InterruptedException and ExecutionException
            return installResponse.getRelease();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw new InstallFailedException("Execute installation failed (maybe instanceId (name) already exists?)", e);
        }

        throw new InstallFailedException("Unknown reason");
    }

    public ReleaseOuterClass.Release uninstallChart(String instanceId) throws IOException, UninstallFailedException {
        return uninstallChart(instanceId, 300);
    }

    @NotNull
    public ReleaseOuterClass.Release uninstallChart(String instanceId, long timeout) throws IOException, UninstallFailedException {

        try (DefaultKubernetesClient kubernetesClient = new DefaultKubernetesClient();
             Tiller tiller = new Tiller(kubernetesClient);
             ReleaseManager releaseManager = new ReleaseManager(tiller)) {

            UninstallReleaseRequest.Builder requestBuilder = UninstallReleaseRequest.newBuilder();
            requestBuilder.setTimeout(timeout);
            requestBuilder.setPurge(true);
            requestBuilder.setName(instanceId);

            Future<UninstallReleaseResponse> uninstallResponseFuture = releaseManager.uninstall(requestBuilder.build());
            ReleaseOuterClass.Release release = uninstallResponseFuture.get().getRelease();
            return release;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw new UninstallFailedException("Execute uninstall failed (maybe instance doesn't exist?)", e);
        }

        throw new UninstallFailedException("Unknown reason");
    }
}
