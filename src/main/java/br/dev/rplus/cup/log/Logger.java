package br.dev.rplus.cup.log;

import java.io.File;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

/**
 * LoggerCup is an abstract class that facilitates logging management for Java applications.
 * It allows the initialization of a custom logger with options like file logging and log levels.
 * <p>
 * The class is designed to be used across the application as a centralized logging tool.
 */
public abstract class Logger {
    /**
     * Logger instance.
     */
    private static java.util.logging.Logger logger;

    /**
     * Logger identifier.
     */
    private static String id;

    /**
     * Default log level.
     */
    private static final String DEFAULT_LEVEL = "INFO";

    /**
     * Initializes the logger with default settings.
     */
    public static void init() {
        init("br.dev.rplus", false, DEFAULT_LEVEL);
    }

    /**
     * Initializes the logger with a custom identifier.
     *
     * @param id Logger identifier.
     */
    public static void init(String id) {
        init(id, false, DEFAULT_LEVEL);
    }

    /**
     * Initializes the logger with a custom identifier and an option to log to a file.
     *
     * @param id     Logger identifier.
     * @param toFile Indicates whether the log should be written to a file.
     */
    public static void init(String id, boolean toFile) {
        init(id, toFile, DEFAULT_LEVEL);
    }

    /**
     * Initializes the logger with a custom identifier, an option to log to a file, and a specified log level.
     *
     * @param id     Logger identifier.
     * @param toFile Indicates whether the log should be written to a file.
     * @param level  Log level (e.g., "INFO", "DEBUG").
     */
    public static synchronized void init(String id, boolean toFile, String level) {
        try {
            if (logger == null) {
                setId(id);
                logger = java.util.logging.Logger.getLogger(id);
                logFile(toFile);
                setLevel(level);
                logger.setUseParentHandlers(false); // Disable default console handler
                addCustomConsoleHandler(); // Add custom handler for colored output
            }
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(LevelCup.CRITICAL, "Error initializing logging.", e);
        }
    }

    /**
     * Adds a custom handler to the logger for formatting and console output.
     */
    private static void addCustomConsoleHandler() {
        Handler consoleHandler = new ConsoleHandler() {
            @Override
            protected void setOutputStream(OutputStream out) throws SecurityException {
                super.setOutputStream(System.out);
            }
        };
        consoleHandler.setFormatter(new LoggerFormatter());
        logger.addHandler(consoleHandler);
    }

    /**
     * Returns the logger instance.
     *
     * @return Logger instance.
     */
    public static java.util.logging.Logger getLogger() {
        if (logger == null) {
            init();
        }
        return logger;
    }

    /**
     * Sets the logger identifier.
     *
     * @param id Logger identifier.
     */
    public static void setId(String id) {
        Logger.id = id;
    }

    /**
     * Returns the logger identifier.
     *
     * @return Logger identifier.
     */
    public static String getId() {
        return id;
    }

    /**
     * Sets the log level for the logger.
     *
     * @param level Log level.
     */
    public static void setLevel(Level level) {
        getLogger().setLevel(level);
        for (Handler handler : getLogger().getHandlers()) {
            handler.setLevel(level);
        }
    }

    /**
     * Sets the log level for the logger using a string value.
     *
     * @param level Log level as a string.
     */
    public static void setLevel(String level) {
        setLevel(levelParser(level));
    }

    /**
     * Converts a string log level to a {@link Level} object.
     *
     * @param level Log level as a string.
     * @return {@link Level} object.
     */
    private static Level levelParser(String level) {
        try {
            return LevelCup.parse(level);
        } catch (IllegalArgumentException e) {
            return Level.parse(DEFAULT_LEVEL);
        }
    }

    /**
     * Generates the log file name based on the current date and logger identifier.
     *
     * @return Absolute path of the log file.
     */
    private static String getFile() {
        File file;
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            String path = processMessage("%s-%s-%s_%s.log", dft.format(LocalDateTime.now()), getId());
            file = new File(path);
            if (!file.getParentFile().exists()) {
                boolean created = file.getParentFile().mkdirs();
                if (!created) {
                    throw new Exception();
                }
            }
            return file.getAbsolutePath();
        } catch (Exception e) {
            return "cup.log";
        }
    }

    /**
     * Configures the logger to write logs to a file.
     *
     * @param toFile Indicates whether the log should be written to a file.
     */
    public static void logFile(boolean toFile) {
        if (toFile) {
            FileHandler fh;
            try {
                fh = new FileHandler(getFile(), true);
                fh.setFormatter(new LoggerFormatter());
                getLogger().addHandler(fh);
            } catch (Exception e) {
                error("Error creating log file. Error:<br>%s", e);
            }
        }
    }

    /**
     * Processes the log message, formatting it and appending exceptions if present.
     *
     * @param message Log message.
     * @param params  Additional parameters to format the message.
     * @return Processed message.
     */
    private static String processMessage(String message, Object... params) {
        List<Throwable> exceptions = new ArrayList<>();
        List<Object> paramsList = new ArrayList<>();
        try {
            if (params.length == 0) {
                return message;
            }

            for (Object param : params) {
                if (param instanceof Throwable) {
                    exceptions.add((Throwable) param);
                } else {
                    paramsList.add(param);
                }
            }

            String formattedMessage = message.replaceAll("<br>", System.lineSeparator());
            if (!paramsList.isEmpty()) {
                formattedMessage = String.format(formattedMessage, paramsList.toArray());
            }

            if (!exceptions.isEmpty()) {
                StringBuilder sb = new StringBuilder(" Exceptions:\n");
                for (Throwable throwable : exceptions) {
                    sb.append(throwable.getMessage()).append("\n");
                }

                formattedMessage += sb.toString();
            }
            return formattedMessage;
        } catch (Exception e) {
            return message;
        } finally {
            exceptions.clear();
            paramsList.clear();
        }
    }

    /**
     * Logs a message at the specified log level.
     *
     * @param level   Log level.
     * @param message Log message.
     */
    private static void log(Level level, String message) {
        try {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
            getLogger().logp(level, ste.getClassName(), ste.getMethodName(), message);
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, "Error logging message.", e);
        }
    }

    /**
     * Logs a message at the DEBUG level.
     *
     * @param message Log message.
     * @param params  Additional parameters to format the message.
     */
    public static void debug(String message, Object... params) {
        log(LevelCup.DEBUG, processMessage(message, params));
    }

    /**
     * Logs a message at the INFO level.
     *
     * @param message Log message.
     * @param params  Additional parameters to format the message.
     */
    public static void info(String message, Object... params) {
        log(LevelCup.INFO, processMessage(message, params));
    }

    /**
     * Logs a message at the NOTICE level.
     *
     * @param message Log message.
     * @param params  Additional parameters to format the message.
     */
    public static void notice(String message, Object... params) {
        log(LevelCup.NOTICE, processMessage(message, params));
    }

    /**
     * Logs a message at the WARNING level.
     *
     * @param message Log message.
     * @param params  Additional parameters to format the message.
     */
    public static void warn(String message, Object... params) {
        log(LevelCup.WARNING, processMessage(message, params));
    }

    /**
     * Logs a message at the ERROR level.
     *
     * @param message Log message.
     * @param params  Additional parameters to format the message.
     */
    public static void error(String message, Object... params) {
        log(LevelCup.ERROR, processMessage(message, params));
    }

    /**
     * Logs a message at the CRITICAL level.
     *
     * @param message Log message.
     * @param params  Additional parameters to format the message.
     */
    public static void critical(String message, Object... params) {
        log(LevelCup.CRITICAL, processMessage(message, params));
    }
}
