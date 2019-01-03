package com.cyrillrx.logger.extension;

import com.cyrillrx.logger.LogChild;
import com.cyrillrx.logger.LogHelper;
import com.cyrillrx.logger.Severity;
import com.cyrillrx.logger.SeverityLogChild;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A ready-to-use severity-aware {@link LogChild} wrapping <code>System.out#println(String)</code> class.
 *
 * @author Cyril Leroux
 *         Created on 18/10/2015.
 */
public class SystemOutLog extends SeverityLogChild {

    public SystemOutLog(int severity) {
        super(severity);
    }

    @Override
    protected void doLog(int severity, String tag, String message, Throwable throwable) {

        if (throwable == null) {
            simpleLog(severity, tag, message);
            return;
        }

        final String stackTrace = LogHelper.getStackTrace(throwable);
        if (stackTrace == null) {
            simpleLog(severity, tag, message);
            return;
        }

        logWithStackTrace(severity, tag, message, stackTrace);
    }

    private static void simpleLog(int severity, String tag, String message) {
        println("%s - %s - %s - %s", getCurrentDateTime(), Severity.getLabel(severity), tag, LogHelper.getDetailedLog(message));
    }

    private static void logWithStackTrace(int severity, String tag, String message, String stackTrace) {
        println("%s - %s - %s - %s\n%s", getCurrentDateTime(), Severity.getLabel(severity), tag, LogHelper.getDetailedLog(message), stackTrace);
    }

    /**
     * Prints a String into the "standard" output stream and then terminate the line.
     *
     * @param x The <code>String</code> to be printed.
     */
    private static void println(String x) { System.out.println(x); }

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