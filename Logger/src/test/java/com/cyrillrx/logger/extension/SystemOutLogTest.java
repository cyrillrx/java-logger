package com.cyrillrx.logger.extension;

import com.cyrillrx.logger.L;
import com.cyrillrx.logger.LogChild;
import com.cyrillrx.logger.Severity;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Cyril Leroux
 *         Created on 03/05/16.
 */
public class SystemOutLogTest {

    private static final String TAG = SystemOutLogTest.class.getSimpleName();

    @BeforeClass
    public static void initLogger() {
        L.initialize();
    }

    @AfterClass
    public static void releaseLogger() {
        L.release();
    }

    @Test
    public void testLoggerVerbose() { testLogger(new SystemOutLog(Severity.VERBOSE), "Testing verbose"); }

    @Test
    public void testLoggerDebug() { testLogger(new SystemOutLog(Severity.DEBUG), "Testing debug"); }

    @Test
    public void testLoggerInfo() { testLogger(new SystemOutLog(Severity.INFO), "Testing info"); }

    @Test
    public void testLoggerWarning() { testLogger(new SystemOutLog(Severity.WARN), "Testing warning"); }

    @Test
    public void testLoggerError() { testLogger(new SystemOutLog(Severity.ERROR), "Testing error"); }

    private static void testLogger(LogChild child, String message) {

        L.addChild(child);

        final Exception dummyException = new Exception("This is an exception");

        logVerbose(message, dummyException);
        logDebug(message, dummyException);
        logInfo(message, dummyException);
        logWarning(message, dummyException);
        logError(message, dummyException);

        L.removeChild(child);
    }

    private static void logVerbose(String message, Exception e) {

        L.verbose(TAG, message);
        L.verbose(TAG, message + " with exception", e);
        logWithSeverity(Severity.VERBOSE, message, e);
    }

    private static void logDebug(String message, Exception e) {

        L.debug(TAG, message);
        L.debug(TAG, message + " with exception", e);
        logWithSeverity(Severity.DEBUG, message, e);
    }

    private static void logInfo(String message, Exception e) {

        L.info(TAG, message);
        L.info(TAG, message + " with exception", e);
        logWithSeverity(Severity.INFO, message, e);
    }

    private static void logWarning(String message, Exception e) {

        L.warning(TAG, message);
        L.warning(TAG, message + " with exception", e);
        logWithSeverity(Severity.WARN, message, e);
    }

    private static void logError(String message, Exception e) {

        L.error(TAG, message);
        L.error(TAG, message + " with exception", e);
        logWithSeverity(Severity.ERROR, message, e);
    }

    private static void logWithSeverity(int severity, String message, Exception e) {
        L.log(severity, TAG, message);
        L.log(severity, TAG, message + " with exception", e);
    }
}
