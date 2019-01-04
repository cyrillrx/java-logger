package com.cyrillrx.logger;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogHelper {

    private static final String LOGGER_CLASS_NAME = Logger.class.getName();

    public static String getDetailedLog(String message) {

        final Thread currentThread = Thread.currentThread();
        final StackTraceElement[] stackElements = currentThread.getStackTrace();

        final StringBuilder sb = new StringBuilder();
        sb.append(message);

        sb.append("\n    Source: ");

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

        sb.append(String.format("%s (thread: %s)", linkableMethod(trace), currentThread.getName()));
        return sb.toString();
    }

    private static String linkableMethod(StackTraceElement trace) {
        if (trace == null) {
            return "trace is null";
        }
        return String.format("%s.%s(%s:%d)", trace.getClassName(), trace.getMethodName(), trace.getFileName(), trace.getLineNumber());
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
