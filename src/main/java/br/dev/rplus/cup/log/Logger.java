package br.dev.rplus.cup.log;

import java.io.File;
import java.io.OutputStream;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import java.util.regex.Pattern;

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
    private static String application;

    /**
     * Default log level.
     */
    private static final String DEFAULT_LEVEL = "INFO";

    /**
     * Private constructor to prevent instantiation.
     */
    private Logger() {}

    /**
     * Initializes the logger with default settings.
     */
    public static void init() {
        init("br.dev.rplus", DEFAULT_LEVEL, false);
    }

    /**
     * Initializes the logger with a custom identifier.
     *
     * @param application logger identifier.
     */
    public static void init(String application) {
        init(application, DEFAULT_LEVEL, false);
    }

    /**
     * Initializes the logger with a custom identifier and an option to log to a file.
     *
     * @param application logger identifier.
     * @param level       log level (e.g., "INFO", "DEBUG").
     */
    public static void init(String application, String level) {
        init(application, level, false);
    }

    /**
     * Initializes the logger with a custom identifier, an option to log to a file, and a specified log level.
     *
     * @param application logger identifier.
     * @param level       log level (e.g., "INFO", "DEBUG").
     * @param toFile      indicates whether the log should be written to a file.
     */
    public static synchronized void init(String application, String level, boolean toFile) {
        try {
            if (logger == null) {
                setApplication(application);
                logger = java.util.logging.Logger.getLogger(application);
                setLevel(level);
                addCustomConsoleHandler();
                logFile(toFile);
                logger.setUseParentHandlers(false);
            }
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(LoggerLevel.FATAL, "Error initializing logging.", e);
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
        consoleHandler.setLevel(logger.getLevel());
        logger.addHandler(consoleHandler);
    }

    /**
     * Returns the logger instance.
     *
     * @return logger instance.
     */
    public static java.util.logging.Logger getLogger() {
        if (logger == null) {
            init();
        }
        return logger;
    }

    /**
     * Sets the application identifier.
     *
     * @param application logger identifier.
     */
    public static void setApplication(String application) {
        Logger.application = application;
    }

    /**
     * Returns the application identifier.
     *
     * @return application identifier.
     */
    public static String getApplication() {
        return application;
    }

    /**
     * Returns the log level for the logger.
     *
     * @return log level.
     */
    public static Level getLevel() {
        return getLogger().getLevel();
    }

    /**
     * Sets the log level for the logger.
     *
     * @param level log level.
     */
    public static void setLevel(Level level) {
        getLogger().setLevel(level);
        try {
            java.util.logging.Logger.getGlobal().getParent().getHandlers()[0].setLevel(level);
        } catch (Exception e) {
            for (Handler handler : getLogger().getHandlers()) {
                handler.setLevel(level);
            }
        }
    }

    /**
     * Sets the log level for the logger using a string value.
     *
     * @param level log level as a string.
     */
    public static void setLevel(String level) {
        setLevel(levelParser(level));
    }

    /**
     * Converts a string log level to a {@link Level} object.
     *
     * @param level log level as a string.
     * @return {@link Level} object.
     */
    private static Level levelParser(String level) {
        try {
            return LoggerLevel.parse(level);
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
            String path = processMessage("%s-%s-%s_%s.log", dft.format(LocalDateTime.now()), getApplication());
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
     * @param toFile indicates whether the log should be written to a file.
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
     * @param message log message.
     * @param params  additional parameters to format the message.
     * @return processed message.
     */
    private static String processMessage(String message, Object... params) {
        if (message == null || message.isEmpty()) {
            return "";
        }

        List<Throwable> exceptions = new ArrayList<>();
        List<Object> paramsList = new ArrayList<>();
        try {
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
                    for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
                        sb.append(stackTraceElement).append("\n");
                    }
                }
                formattedMessage = formattedMessage + sb;
            }

            return removeAccentsAndSpecialCharacters(formattedMessage);
        } catch (Exception e) {
            return message;
        } finally {
            exceptions.clear();
            paramsList.clear();
        }
    }

    /**
     * Removes accents and special characters from a string.
     *
     * @param str input string.
     * @return string without accents and special characters.
     */
    private static String removeAccentsAndSpecialCharacters(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{M}");
        return pattern.matcher(normalized).replaceAll("").replaceAll("[^\\p{ASCII}]", "");
    }

    /**
     * Logs a message at the specified log level.
     *
     * @param level   log level.
     * @param message log message.
     */
    private static void log(Level level, String message) {
        try {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
            getLogger().logp(level, ste.getClassName(), ste.getMethodName(), message);
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(LoggerLevel.FATAL, "Error logging message.", e);
        }
    }

    /**
     * Logs a message at the TRACE level.
     *
     * @param message log message.
     * @param params  additional parameters to format the message.
     */
    public static void trace(String message, Object... params) {
        log(LoggerLevel.TRACE, processMessage(message, params));
    }

    /**
     * Logs a message at the DEBUG level.
     *
     * @param message log message.
     * @param params  additional parameters to format the message.
     */
    public static void debug(String message, Object... params) {
        log(LoggerLevel.DEBUG, processMessage(message, params));
    }

    /**
     * Logs a message at the INFO level.
     *
     * @param message log message.
     * @param params  additional parameters to format the message.
     */
    public static void info(String message, Object... params) {
        log(LoggerLevel.INFO, processMessage(message, params));
    }

    /**
     * Logs a message at the NOTICE level.
     *
     * @param message log message.
     * @param params  additional parameters to format the message.
     */
    public static void notice(String message, Object... params) {
        log(LoggerLevel.NOTICE, processMessage(message, params));
    }

    /**
     * Logs a message at the WARNING level.
     *
     * @param message log message.
     * @param params  additional parameters to format the message.
     */
    public static void warn(String message, Object... params) {
        log(LoggerLevel.WARN, processMessage(message, params));
    }

    /**
     * Logs a message at the ERROR level.
     *
     * @param message log message.
     * @param params  additional parameters to format the message.
     */
    public static void error(String message, Object... params) {
        log(LoggerLevel.ERROR, processMessage(message, params));
    }

    /**
     * Logs a message at the FATAL level.
     *
     * @param message log message.
     * @param params  additional parameters to format the message.
     */
    public static void fatal(String message, Object... params) {
        log(LoggerLevel.FATAL, processMessage(message, params));
    }
}
