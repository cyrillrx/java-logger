package com.cyrillrx.logger;

/**
 * A {@link LogChild} wrapper aware of the log severity level.
 *
 * @author Cyril Leroux
 *         Created on 20/10/15
 */
public abstract class LogWrapper implements LogChild {

    private final int severity;
    private final LogChild wrapped;

    public LogWrapper(int severity, LogChild logger) {
        this.severity = severity;
        wrapped = logger;
    }

    @Override
    public void log(int severity, String tag, String message, Throwable throwable) {
        if (this.severity < severity) {
            return;
        }
        wrapped.log(severity, tag, message, throwable);
    }

    @Override
    public void log(int severity, String tag, String message) {
        if (this.severity < severity) {
            return;
        }
        wrapped.log(severity, tag, message);
    }
}
