package com.cyrillrx.logger;

import java.util.HashSet;
import java.util.Set;

/**
 * This class wraps instances of the {@link LogChild} interface.<br />
 * It allows to customize the logging conditions.
 *
 * @author Cyril Leroux
 *         Created on 03/09/12
 */
@SuppressWarnings("unused")
public class Logger {

    private static final String ERROR_ALREADY_INITIALIZED = "initialize() has already been called.";
    private static final String ERROR_INITIALIZE_FIRST    = "Call initialize() before using the Logger.";
    private static Logger sInstance;

    private final Set<LogChild> mLoggers;

    /**
     */
    private Logger() {
        mLoggers = new HashSet<>();
    }

    /**
     * Initializes the Logger.
     */
    public static void initialize() {
        checkMultiInitialization();

        sInstance = new Logger();
    }

    public static synchronized void addChild(LogChild child) {
        checkInitialized();

        sInstance.mLoggers.add(child);
    }

    public static synchronized void removeChild(LogChild child) {
        checkInitialized();

        sInstance.mLoggers.remove(child);
    }

    public static synchronized void verbose(String tag, String message) {
        checkInitialized();

        for (LogChild log : sInstance.mLoggers) {
            log.verbose(tag, message);
        }
    }

    public static synchronized void verbose(String tag, String message, Throwable throwable) {
        checkInitialized();

        for (LogChild log : sInstance.mLoggers) {
            log.verbose(tag, message, throwable);
        }
    }

    public static synchronized void debug(String tag, String message) {
        checkInitialized();

        for (LogChild log : sInstance.mLoggers) {
            log.debug(tag, message);
        }
    }

    public static synchronized void debug(String tag, String message, Throwable throwable) {
        checkInitialized();

        for (LogChild log : sInstance.mLoggers) {
            log.debug(tag, message, throwable);
        }
    }

    public static synchronized void info(String tag, String message) {
        checkInitialized();

        for (LogChild log : sInstance.mLoggers) {
            log.info(tag, message);
        }
    }

    public static synchronized void info(String tag, String message, Throwable throwable) {
        checkInitialized();

        for (LogChild log : sInstance.mLoggers) {
            log.info(tag, message, throwable);
        }
    }

    public static synchronized void warning(String tag, String message) {
        checkInitialized();

        for (LogChild log : sInstance.mLoggers) {
            log.warning(tag, message);
        }
    }

    public static synchronized void warning(String tag, String message, Throwable throwable) {
        checkInitialized();

        for (LogChild log : sInstance.mLoggers) {
            log.warning(tag, message, throwable);
        }
    }

    public static synchronized void error(String tag, String message) {
        checkInitialized();

        for (LogChild log : sInstance.mLoggers) {
            log.error(tag, message);
        }
    }

    public static synchronized void error(String tag, String message, Throwable throwable) {
        checkInitialized();

        for (LogChild log : sInstance.mLoggers) {
            log.error(tag, message, throwable);
        }
    }

    /**
     * Checks whether the component has been initialized.<br />
     * Throws if not.
     */
    private static void checkInitialized() {
        if (sInstance == null) {
            throw new IllegalStateException(ERROR_INITIALIZE_FIRST);
        }
    }

    /**
     * Prevents multiple initialization of the component.<br />
     * Throws if the component has already been initialized.
     */
    private static void checkMultiInitialization() {
        if (sInstance != null) {
            throw new IllegalStateException(ERROR_ALREADY_INITIALIZED);
        }
    }
}
