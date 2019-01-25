package de.thbingen.epro.project.servicebroker.helm.exceptions;

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
