package br.dev.rplus.cup.log;

import br.dev.rplus.cup.others.AnsiColors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * LoggerFormatter is a custom formatter for log messages, designed to format
 * log output with timestamp, log level, source class, method name, and thread name.
 * It also adds ANSI color codes to the output for better readability in console environments.
 */
public class LoggerFormatter extends Formatter {

    /**
     * Formats the given log record and returns a formatted string.
     *
     * @param record The log record to be formatted.
     * @return The formatted log message as a string.
     */
    @Override
    public String format(LogRecord record) {
        String timestamp = this.colorizeMessage(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS")), AnsiColors.LIGHT_BLACK.getColorCode());

        String colorCode = getColorForLevel(record.getLevel());
        String level = this.colorizeMessage(String.format("%-8s", record.getLevel().getName()), colorCode);

        String className = this.colorizeMessage(record.getSourceClassName(), AnsiColors.BLUE.getColorCode());
        String methodName = this.colorizeMessage(String.format(".%s", record.getSourceMethodName()), AnsiColors.BLUE.getColorCode());

        String threadName = this.colorizeMessage(Thread.currentThread().getName(), AnsiColors.CYAN.getColorCode());

        return String.format("%s %s [%s%s()] (%s) %s%n", timestamp, level, className, methodName, threadName, formatMessage(record));
    }

    /**
     * Determines the appropriate ANSI color code for the given log level.
     *
     * @param level The log level for which to determine the color code.
     * @return The ANSI color code as a string.
     */
    private String getColorForLevel(Level level) {
        if (level == LevelCup.DEBUG) return AnsiColors.LIGHT_BLUE.getColorCode();
        if (level == LevelCup.INFO) return AnsiColors.LIGHT_GREEN.getColorCode();
        if (level == LevelCup.NOTICE) return AnsiColors.LIGHT_CYAN.getColorCode();
        if (level == LevelCup.WARNING) return AnsiColors.LIGHT_YELLOW.getColorCode();
        if (level == LevelCup.ERROR) return AnsiColors.RED.getColorCode();
        if (level == LevelCup.CRITICAL) return AnsiColors.LIGHT_RED.getColorCode();

        return AnsiColors.RESET.getColorCode();
    }

    /**
     * Adds ANSI color codes to the given message based on the provided color code.
     *
     * @param message   The message to be colorized.
     * @param colorCode The ANSI color code to apply to the message.
     * @return The colorized message as a string.
     */
    private String colorizeMessage(String message, String colorCode) {
        return String.format("%s%s%s", colorCode, message, AnsiColors.RESET.getColorCode());
    }
}