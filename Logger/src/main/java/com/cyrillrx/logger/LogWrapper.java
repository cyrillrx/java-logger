package com.cyrillrx.logger;

/**
 * A {@link LogChild} wrapper aware of the log severity level.
 *
 * @author Cyril Leroux
 *         Created on 20/10/15
 */
public abstract class LogWrapper implements LogChild {

    private final int mSeverity;
    private final LogChild mWrapped;

    public LogWrapper(int severity, LogChild logger) {
        mSeverity = severity;
        mWrapped = logger;
    }

    @Override
    public void log(int severity, String tag, String message, Throwable throwable) {
        if (mSeverity < severity) {
            return;
        }
        mWrapped.log(severity, tag, message, throwable);
    }

    @Override
    public void log(int severity, String tag, String message) {
        if (mSeverity < severity) {
            return;
        }
        mWrapped.log(severity, tag, message);
    }
}
