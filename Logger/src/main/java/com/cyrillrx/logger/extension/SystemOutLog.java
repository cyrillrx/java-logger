package com.cyrillrx.logger.extension;

import com.cyrillrx.logger.LogChild;
import com.cyrillrx.logger.LogWrapper;
import com.cyrillrx.logger.Severity;

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
        public void log(int severity, String tag, String message) {
            println("%s - %s - %s - %s", getCurrentDateTime(), Severity.getLabel(severity), tag, message);
        }

        /**
         * @param tag       Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
         * @param message   The message you would like to log.
         * @param throwable An exception to log.
         */
        @Override
        public void log(int severity, String tag, String message, Throwable throwable) {

            if (throwable == null) {
                log(severity, tag, message);
                return;
            }

            final String stackTrace = getStackTrace(throwable);
            if (stackTrace == null) {
                log(severity, tag, message);
                return;
            }

            println("%s - %s - %s - %s\n%s", getCurrentDateTime(), Severity.getLabel(severity), tag, message, stackTrace);
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