package de.thbingen.epro.project.servicebroker.helm.exceptions;

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
