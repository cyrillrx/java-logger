package com.cyrillrx.logger;

/**
 * A {@link LogChild} wrapper aware of the log severity level.
 *
 * @author Cyril Leroux
 *         Created on 20/10/15
 */
public abstract class SeverityLogChild extends LogChild {

    private final int severity;

    public SeverityLogChild(int severity) {
        this.severity = severity;
    }

    @Override
    protected boolean shouldLog(int severity, String tag, String message, Throwable throwable) {
        return this.severity >= severity;
    }
}
