package br.dev.rplus.cup.log;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.OutputStream;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.regex.Pattern;

/**
 * LoggerCup is an abstract class that facilitates logging management for Java applications.
 * It allows the initialization of a custom logger with options like file logging and log levels.
 * <p>
 * The class is designed to be used across the application as a centralized logging tool.
 */
@Setter
@Getter
public class Logger {

    /**
     * Logger instance.
     */
    private static java.util.logging.Logger logger;

    private String application;

    /**
     * Default log level.
     */
    private static final String DEFAULT_LEVEL = "INFO";

    private static Logger instance;

    /**
     * Returns the singleton instance of the LoggerCup.
     *
     * @return the LoggerCup instance.
     */
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    /**
     * Initializes the logger with default settings.
     */
    public void init() {
        this.init("br.dev.rplus", DEFAULT_LEVEL, false, false);
    }

    /**
     * Initializes the logger with a custom identifier.
     *
     * @param application logger identifier.
     */
    public void init(String application) {
        this.init(application, DEFAULT_LEVEL);
    }

    /**
     * Initializes the logger with a custom identifier and an option to log to a file.
     *
     * @param application logger identifier.
     * @param level       log level (e.g., "INFO", "DEBUG").
     */
    public void init(String application, String level) {
        this.init(application, level, false);
    }

    public void init(String application, String level, boolean useCustomHandler) {
        this.init(application, level, useCustomHandler, false);
    }

    /**
     * Initializes the logger with a custom identifier, an option to log to a file, and a specified log level.
     *
     * @param application      logger identifier.
     * @param level            log level (e.g., "INFO", "DEBUG").
     * @param useCustomHandler indicates whether to use a custom console handler.
     * @param toFile           indicates whether the log should be written to a file.
     */
    public synchronized void init(String application, String level, boolean useCustomHandler, boolean toFile) {
        try {
            if (logger == null) {
                this.setApplication(application);
                logger = java.util.logging.Logger.getLogger(application);
                this.setLevel(level);
                if (useCustomHandler) {
                    this.addCustomConsoleHandler();
                }
                this.logFile(toFile);
            }
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(LoggerLevel.FATAL, "Error initializing logging.", e);
        }
    }

    /**
     * Adds a custom handler to the logger for formatting and console output.
     */
    private void addCustomConsoleHandler() {
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
    public java.util.logging.Logger getLogger() {
        if (logger == null) {
            init();
        }
        return logger;
    }

    /**
     * Returns the logger instance for a specific application.
     *
     * @param application logger identifier.
     * @return logger instance.
     */
    public static java.util.logging.Logger getLogger(String application) {
        logger = java.util.logging.Logger.getLogger(application);
        return logger;
    }

    /**
     * Returns the log level for the logger.
     *
     * @return log level.
     */
    public Level getLevel() {
        return this.getLogger().getLevel();
    }

    /**
     * Sets the log level for the logger.
     *
     * @param level log level.
     */
    public void setLevel(Level level) {
        this.getLogger().setLevel(level);
        try {
            java.util.logging.Logger.getGlobal().getParent().getHandlers()[0].setLevel(level);
        } catch (Exception e) {
            for (Handler handler : this.getLogger().getHandlers()) {
                handler.setLevel(level);
            }
        }
    }

    /**
     * Sets the log level for the logger using a string value.
     *
     * @param level log level as a string.
     */
    public void setLevel(String level) {
        this.setLevel(levelParser(level));
    }

    /**
     * Converts a string log level to a {@link Level} object.
     *
     * @param level log level as a string.
     * @return {@link Level} object.
     */
    private Level levelParser(String level) {
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
    private String getFile() {
        File file;
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            String path = this.processMessage("%s-%s-%s_%s.log", dft.format(LocalDateTime.now()), this.getApplication());
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
    public void logFile(boolean toFile) {
        if (toFile) {
            FileHandler fh;
            try {
                fh = new FileHandler(this.getFile(), true);
                fh.setFormatter(new LoggerFormatter());
                this.getLogger().addHandler(fh);
            } catch (Exception e) {
                this.error("Error creating log file. Error:<br>%s", e);
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
    private String processMessage(String message, Object... params) {
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

            return this.removeAccentsAndSpecialCharacters(formattedMessage);
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
    private String removeAccentsAndSpecialCharacters(String str) {
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
    private void log(Level level, String message) {
        try {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
            this.getLogger().logp(level, ste.getClassName(), ste.getMethodName(), message);
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
    public void trace(String message, Object... params) {
        this.log(LoggerLevel.TRACE, this.processMessage(message, params));
    }

    /**
     * Logs a message at the DEBUG level.
     *
     * @param message log message.
     * @param params  additional parameters to format the message.
     */
    public void debug(String message, Object... params) {
        this.log(LoggerLevel.DEBUG, this.processMessage(message, params));
    }

    /**
     * Logs a message at the INFO level.
     *
     * @param message log message.
     * @param params  additional parameters to format the message.
     */
    public void info(String message, Object... params) {
        this.log(LoggerLevel.INFO, this.processMessage(message, params));
    }

    /**
     * Logs a message at the NOTICE level.
     *
     * @param message log message.
     * @param params  additional parameters to format the message.
     */
    public void notice(String message, Object... params) {
        this.log(LoggerLevel.NOTICE, this.processMessage(message, params));
    }

    /**
     * Logs a message at the WARNING level.
     *
     * @param message log message.
     * @param params  additional parameters to format the message.
     */
    public void warn(String message, Object... params) {
        this.log(LoggerLevel.WARN, this.processMessage(message, params));
    }

    /**
     * Logs a message at the ERROR level.
     *
     * @param message log message.
     * @param params  additional parameters to format the message.
     */
    public void error(String message, Object... params) {
        this.log(LoggerLevel.ERROR, this.processMessage(message, params));
    }

    /**
     * Logs a message at the FATAL level.
     *
     * @param message log message.
     * @param params  additional parameters to format the message.
     */
    public void fatal(String message, Object... params) {
        this.log(LoggerLevel.FATAL, this.processMessage(message, params));
    }
}
