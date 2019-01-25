/**
 * TODO add description
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.servicebroker.services;

import hapi.services.tiller.Tiller;
import hapi.version.VersionOuterClass;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import org.microbean.helm.ReleaseManager;
import org.microbean.helm.TillerInstaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class HelmService {

    private static final Logger LOG = LoggerFactory.getLogger(HelmService.class);
    private final boolean upgradeVersion = false;
    private final long connectionTimeout = 300L;

    public void initTiller(boolean upgradeVersion, long connectionTimeout) {
        TillerInstaller tillerInstaller = new TillerInstaller();
        tillerInstaller.init(upgradeVersion, connectionTimeout);
    }

    public void installChart(URI path) {
    }

    public void deleteChart(String name) {
        deleteChart(name, false);
    }

    public void deleteChart(String name, boolean purge) {
        Tiller.UninstallReleaseRequest.Builder builder =
                Tiller.UninstallReleaseRequest.newBuilder();

        builder.setName(name);
        builder.setTimeout(300L);
        builder.setPurge(purge);

        org.microbean.helm.Tiller tiller = null;

        try {
            tiller = getTiller();
            LOG.debug("Tiller Version: " + tiller.getVersion().getSemVer());

            if (tiller != null) {

                ReleaseManager manager = new ReleaseManager(tiller);
                Tiller.UninstallReleaseRequest request = builder.build();

                Future<Tiller.UninstallReleaseResponse> responseFuture
                        = manager.uninstall(request);

                hapi.services.tiller.Tiller.UninstallReleaseResponse response
                        = responseFuture.get();

            }


        } catch (IOException | InterruptedException | ExecutionException e) {
            LOG.error(e.getMessage());
        }

    }

    private org.microbean.helm.Tiller getTiller() throws MalformedURLException {
        DefaultKubernetesClient kubernetesClient = new DefaultKubernetesClient();
        org.microbean.helm.Tiller tiller = new org.microbean.helm.Tiller(kubernetesClient);
        return tiller;
    }

}
