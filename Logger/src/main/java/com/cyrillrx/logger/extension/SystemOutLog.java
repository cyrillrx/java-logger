package com.cyrillrx.logger.extension;

import com.cyrillrx.logger.LogChild;
import com.cyrillrx.logger.LogWrapper;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * A ready-to-use severity-aware {@link LogChild} wrapping <code>System.out#println(String)</code> class.
 *
 * @author Cyril Leroux
 *         Created on 18/10/2015.
 */
public class SystemOutLog extends LogWrapper {

    public SystemOutLog(int severity) {
        super(severity, new SystemOutChild());
    }

    /**
     * System standard output's log child
     */
    private static class SystemOutChild implements LogChild {

        /**
         * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message The message you would like to log.
         */
        @Override
        public void verbose(String tag, String message) {
            println("Verbose - %s - %s", tag, message);
        }

        /**
         * @param tag       Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message   The message you would like to log.
         * @param throwable An exception to log.
         */
        @Override
        public void verbose(String tag, String message, Throwable throwable) {

            if (throwable == null) {
                verbose(tag, message);
                return;
            }

            final String stackTrace = getStackTrace(throwable);
            if (stackTrace == null) {
                verbose(tag, message);
                return;
            }

            println("Verbose - %s - %s\n%s", tag, message, stackTrace);
        }

        /**
         * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message The message you would like to log.
         */
        @Override
        public void debug(String tag, String message) {
            println("Debug - %s - %s", tag, message);
        }

        /**
         * @param tag       Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message   The message you would like to log.
         * @param throwable An exception to log.
         */
        @Override
        public void debug(String tag, String message, Throwable throwable) {

            if (throwable == null) {
                debug(tag, message);
                return;
            }

            final String stackTrace = getStackTrace(throwable);
            if (stackTrace == null) {
                debug(tag, message);
                return;
            }

            println("Debug - %s - %s\n%s", tag, message, stackTrace);
        }

        /**
         * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message The message you would like to log.
         */
        @Override
        public void info(String tag, String message) {
            println("Info - %s - %s", tag, message);
        }

        /**
         * @param tag       Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message   The message you would like to log.
         * @param throwable An exception to log.
         */
        @Override
        public void info(String tag, String message, Throwable throwable) {

            if (throwable == null) {
                info(tag, message);
                return;
            }

            final String stackTrace = getStackTrace(throwable);
            if (stackTrace == null) {
                info(tag, message);
                return;
            }

            println("Info - %s - %s\n%s", tag, message, stackTrace);
        }

        /**
         * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message The message you would like to log.
         */
        @Override
        public void warning(String tag, String message) {
            println("Warning - %s - %s", tag, message);
        }

        /**
         * @param tag       Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message   The message you would like to log.
         * @param throwable An exception to log.
         */
        @Override
        public void warning(String tag, String message, Throwable throwable) {

            if (throwable == null) {
                warning(tag, message);
                return;
            }

            final String stackTrace = getStackTrace(throwable);
            if (stackTrace == null) {
                warning(tag, message);
                return;
            }

            println("Warning - %s - %s\n%s", tag, message, stackTrace);
        }

        /**
         * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message The message you would like to log.
         */
        @Override
        public void error(String tag, String message) {
            println("Error - %s - %s", tag, message);
        }

        /**
         * @param tag       Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message   The message you would like to log.
         * @param throwable An exception to log.
         */
        @Override
        public void error(String tag, String message, Throwable throwable) {

            if (throwable == null) {
                error(tag, message);
                return;
            }

            final String stackTrace = getStackTrace(throwable);
            if (stackTrace == null) {
                error(tag, message);
                return;
            }

            println("Error - %s - %s\n%s", tag, message, stackTrace);
        }
    }

    /**
     * @return The Stack trace as a String.
     */
    private static String getStackTrace(Throwable t) {

        try (final StringWriter sw = new StringWriter();
             final PrintWriter pw = new PrintWriter(sw)) {

            t.printStackTrace(pw);
            return sw.toString();

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Prints a String into the "standard" output stream and then terminate the line.
     *
     * @param x The <code>String</code> to be printed.
     */
    private static void println(String x) {
        System.out.println(x);
    }

    /**
     * Formats data, prints into the "standard" output stream and then terminate the line.
     */
    private static void println(String format, Object... args) {
        println(String.format(format, args));
    }
}