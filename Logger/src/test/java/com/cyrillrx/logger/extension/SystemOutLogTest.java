package com.cyrillrx.logger.extension;

import com.cyrillrx.logger.L;
import com.cyrillrx.logger.LogChild;
import com.cyrillrx.logger.Severity;
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

    @Test
    public void testLoggerVerbose() {
        testLogger(new SystemOutLog(Severity.VERBOSE));
    }

    @Test
    public void testLoggerDebug() {
        testLogger(new SystemOutLog(Severity.DEBUG));
    }

    @Test
    public void testLoggerInfo() {
        testLogger(new SystemOutLog(Severity.INFO));
    }

    @Test
    public void testLoggerWarning() {
        testLogger(new SystemOutLog(Severity.WARN));
    }

    @Test
    public void testLoggerError() {
        testLogger(new SystemOutLog(Severity.ERROR));
    }

    public void testLogger(LogChild child) {

        L.addChild(child);

        L.verbose(TAG, "Test verbose");
        L.debug(TAG, "Test debug");
        L.info(TAG, "Test info");
        L.warning(TAG, "Test warning");
        L.error(TAG, "Test error");

        L.removeChild(child);
    }
}
