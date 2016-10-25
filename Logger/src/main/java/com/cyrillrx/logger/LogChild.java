package com.cyrillrx.logger;

/**
 * @author Cyril Leroux
 *         Created on 18/10/2015.
 */
public interface LogChild {

    /**
     * @param tag       Used to identify the source of a log message.
     *                  It usually identifies the class or activity where the log call occurs.
     * @param message   The message you would like to log.
     * @param throwable An exception to log.
     */
    void log(int severity, String tag, String message, Throwable throwable);

    /**
     * @param tag       Used to identify the source of a log message.
     *                  It usually identifies the class or activity where the log call occurs.
     * @param message   The message you would like to log.
     */
    void log(int severity, String tag, String message);
}
