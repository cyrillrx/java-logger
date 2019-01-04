package com.cyrillrx.logger;

/**
 * @author Cyril Leroux
 *         Created on 18/10/2015.
 */
public abstract class LogChild {

    /**
     * @param severity  The severity level. See {@link Severity}
     * @param tag       Used to identify the source of a log message.
     *                  It usually identifies the class or activity where the log call occurs.
     * @param message   The message you would like to log.
     * @param throwable An exception to log. Might be null.
     */
    public void log(int severity, String tag, String message, Throwable throwable) {
        if (shouldLog(severity, tag, message, throwable)) {
            doLog(severity, tag, message, throwable);
        }
    }

    /**
     * @param severity The severity level. See {@link Severity}
     * @param tag      Used to identify the source of a log message.
     *                 It usually identifies the class or activity where the log call occurs.
     * @param message  The message you would like to log.
     */
    public void log(int severity, String tag, String message) {
        if (shouldLog(severity, tag, message, null)) {
            doLog(severity, tag, message, null);
        }
    }

    protected boolean shouldLog(int severity, String tag, String message, Throwable throwable) { return true; }

    /**
     * @param severity  The severity level. See {@link Severity}
     * @param tag       Used to identify the source of a log message.
     *                  It usually identifies the class or activity where the log call occurs.
     * @param message   The message you would like to log.
     * @param throwable An exception to log. Might be null.
     */
    protected abstract void doLog(int severity, String tag, String message, Throwable throwable);
}
