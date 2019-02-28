package de.thbingen.epro.project.servicebroker.helm.exceptions;

/**
 * {@link Exception} thrown when uninstallation via {@link de.thbingen.epro.project.servicebroker.helm.HelmClient} failed
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */
public class UninstallFailedException extends Exception {
    public UninstallFailedException() {
        super();
    }

    public UninstallFailedException(String s) {
        super(s);
    }

    public UninstallFailedException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public UninstallFailedException(Throwable throwable) {
        super(throwable);
    }

    protected UninstallFailedException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
