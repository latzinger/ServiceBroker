package de.thbingen.epro.project.servicebroker.helm.exceptions;

/**
 * {@link Exception} thrown when installation via {@link de.thbingen.epro.project.servicebroker.helm.HelmClient} failed
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */
public class InstallFailedException extends Exception {
    public InstallFailedException() {
        super();
    }

    public InstallFailedException(String s) {
        super(s);
    }

    public InstallFailedException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InstallFailedException(Throwable throwable) {
        super(throwable);
    }

    protected InstallFailedException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
