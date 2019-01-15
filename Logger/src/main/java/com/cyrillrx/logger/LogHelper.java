package com.cyrillrx.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogHelper {

    private static final String LOGGER_CLASS_NAME = Logger.class.getName();

    /**
     * The ISO-like date-time formatter that formats or parses a date-time with
     * offset and zone, such as '2011-12-03T10:15:30+01:00[Europe/Paris]'
     */
    public static String getCurrentDateTime() { return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME); }

    public static String simpleLog(int severity, String tag, String message) {
        return String.format("%s - %s - %s - %s", getCurrentDateTime(), Severity.getLabel(severity), tag, message);
    }

    public static String logWithStackTrace(int severity, String tag, String message, String stackTrace) {
        return String.format("%s - %s - %s - %s\n%s", getCurrentDateTime(), Severity.getLabel(severity), tag, message, stackTrace);
    }

    public static String getDetailedLog(String message) {

        final Thread currentThread = Thread.currentThread();
        final StackTraceElement[] stackElements = currentThread.getStackTrace();

        final StringBuilder sb = new StringBuilder();
        sb.append(message);

        sb.append(" - ");

        boolean lastWasLoggerClass = false;

        StackTraceElement trace = null;
        for (StackTraceElement stackElement : stackElements) {
            trace = stackElement;

            final String className = trace.getClassName();
            final boolean isLoggerClass = className.startsWith(LOGGER_CLASS_NAME);
            if (lastWasLoggerClass && !isLoggerClass) {
                break;
            }
            lastWasLoggerClass = isLoggerClass;
        }

        sb.append(String.format("%s [thread: %s]", linkableMethod(trace), currentThread.getName()));
        return sb.toString();
    }

    public static String linkableMethod(StackTraceElement trace) {
        if (trace == null) {
            return "trace is null";
        }
        return String.format("%s.%s(%s:%d)",
                trace.getClassName(),
                trace.getMethodName(),
                trace.getFileName(),
                trace.getLineNumber());
    }

    public static String linkableLine(StackTraceElement trace) {
        if (trace == null) {
            return "trace is null";
        }
        return String.format("(%s:%d)", trace.getFileName(), trace.getLineNumber());
    }

    /** @return The Stack trace as a String. */
    public static String getStackTrace(Throwable t) {

        try (final StringWriter sw = new StringWriter();
             final PrintWriter pw = new PrintWriter(sw)) {

            t.printStackTrace(pw);
            return sw.toString();

        } catch (Exception e) {
            return null;
        }
    }
}
