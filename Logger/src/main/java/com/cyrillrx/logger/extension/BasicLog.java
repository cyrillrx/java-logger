package com.cyrillrx.logger.extension;

import com.cyrillrx.logger.LogChild;
import com.cyrillrx.logger.LogWrapper;
import com.cyrillrx.logger.Severity;

/**
 * A ready-to-use severity-aware {@link LogChild} wrapping {@link java.io.PrintStream#println(String)} class.
 *
 * @author Cyril Leroux
 *         Created on 18/10/2015.
 */
public class BasicLog extends LogWrapper {

    public BasicLog(@Severity.LogSeverity int severity) {
        super(severity, new LogKitten());
    }

    /**
     * Log cat child
     */
    private static class LogKitten implements LogChild {

        /**
         * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message The message you would like to log.
         */
        @Override
        public void verbose(String tag, String message) {
            System.out.println(String.format("Verbose - %s - %s", tag, message));
        }

        /**
         * @param tag       Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message   The message you would like to log.
         * @param throwable An exception to log.
         */
        @Override
        public void verbose(String tag, String message, Throwable throwable) {
            System.err.println(String.format("Verbose - %s - %s", tag, message));
        }

        /**
         * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message The message you would like to log.
         */
        @Override
        public void debug(String tag, String message) {
            System.out.println(String.format("Debug - %s - %s", tag, message));
        }

        /**
         * @param tag       Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message   The message you would like to log.
         * @param throwable An exception to log.
         */
        @Override
        public void debug(String tag, String message, Throwable throwable) {
            System.err.println(String.format("Debug - %s - %s", tag, message));
        }

        /**
         * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message The message you would like to log.
         */
        @Override
        public void info(String tag, String message) {
            System.out.println(String.format("Info - %s - %s", tag, message));
        }

        /**
         * @param tag       Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message   The message you would like to log.
         * @param throwable An exception to log.
         */
        @Override
        public void info(String tag, String message, Throwable throwable) {
            System.err.println(String.format("Info - %s - %s", tag, message));
        }

        /**
         * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message The message you would like to log.
         */
        @Override
        public void warning(String tag, String message) {
            System.out.println(String.format("Warning - %s - %s", tag, message));
        }

        /**
         * @param tag       Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message   The message you would like to log.
         * @param throwable An exception to log.
         */
        @Override
        public void warning(String tag, String message, Throwable throwable) {
            System.err.println(String.format("Warning - %s - %s", tag, message));
        }

        /**
         * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message The message you would like to log.
         */
        @Override
        public void error(String tag, String message) {
            System.out.println(String.format("Error - %s - %s", tag, message));
        }

        /**
         * @param tag       Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message   The message you would like to log.
         * @param throwable An exception to log.
         */
        @Override
        public void error(String tag, String message, Throwable throwable) {
            System.err.println(String.format("Error - %s - %s", tag, message));
        }
    }
}
