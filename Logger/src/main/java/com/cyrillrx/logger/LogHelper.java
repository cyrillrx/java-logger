package com.cyrillrx.logger;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogHelper {

    /** How deep to log stack traces by default. */
    private static int DEFAULT_STACK_DEPTH = 7;

    public static String getDetailedLog(String msg) {
        return getDetailedLog(msg, DEFAULT_STACK_DEPTH);
    }

    public static String getDetailedLog(String msg, int depth) {
        final Thread currentThread = Thread.currentThread();
        final StackTraceElement trace = currentThread.getStackTrace()[depth];
        final String logPrefix = String.format("[thread:%s][method:%s] ", currentThread.getName(), linkableMethod(trace));
        return logPrefix + msg;
    }

    private static String linkableMethod(StackTraceElement trace) {
        return String.format("%s(%s:%d)", trace.getMethodName(), trace.getFileName(), trace.getLineNumber());
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
