package com.cyrillrx.logger.extension;

import com.cyrillrx.logger.LogChild;
import com.cyrillrx.logger.LogWrapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
            println("%s - Verbose - %s - %s", getCurrentDateTime(), tag, message);
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

            println("Verbose - %s - %s\n%s", getCurrentDateTime(), tag, message, stackTrace);
        }

        /**
         * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message The message you would like to log.
         */
        @Override
        public void debug(String tag, String message) {
            println("%s - Debug   - %s - %s", getCurrentDateTime(), tag, message);
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

            println("%s - Debug   - %s - %s\n%s", getCurrentDateTime(), tag, message, stackTrace);
        }

        /**
         * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message The message you would like to log.
         */
        @Override
        public void info(String tag, String message) {
            println("%s - Info    - %s - %s", getCurrentDateTime(), tag, message);
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

            println("%s - Info    - %s - %s\n%s", getCurrentDateTime(), tag, message, stackTrace);
        }

        /**
         * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message The message you would like to log.
         */
        @Override
        public void warning(String tag, String message) {
            println("%s - Warning - %s - %s", getCurrentDateTime(), tag, message);
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

            println("%s - Warning - %s - %s\n%s", getCurrentDateTime(), tag, message, stackTrace);
        }

        /**
         * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message The message you would like to log.
         */
        @Override
        public void error(String tag, String message) {
            println("%s - Error   - %s - %s", getCurrentDateTime(), tag, message);
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

            println("%s - Error   - %s - %s\n%s", getCurrentDateTime(), tag, message, stackTrace);
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

    /**
     * Formats data, prints into the "standard" output stream and then terminate the line.
     */
    private static String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}