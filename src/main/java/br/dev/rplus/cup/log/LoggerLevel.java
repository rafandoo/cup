package br.dev.rplus.cup.log;

import java.io.Serial;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * LevelCup is a custom implementation of the {@link Level} class, providing additional log levels
 * beyond the standard levels provided by the Java logging framework.
 */
public class LoggerLevel extends Level {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The FATAL log level designates very severe error events that will presumably lead the application to abort.
     */
    public static final LoggerLevel FATAL = new LoggerLevel("FATAL", 1100);

    /**
     * The ERROR log level designates error events that might still allow the application to continue running.
     */
    public static final LoggerLevel ERROR = new LoggerLevel("ERROR", 1000);

    /**
     * The WARN log level designates potentially harmful situations.
     */
    public static final LoggerLevel WARN = new LoggerLevel("WARN", 900);

    /**
     * The NOTICE log level designates informational messages that highlight the progress of the application at coarse-grained level.
     */
    public static final LoggerLevel NOTICE = new LoggerLevel("NOTICE", 850);

    /**
     * The INFO log level designates informational messages that highlight the progress of the application at fine-grained level.
     */
    public static final LoggerLevel INFO = new LoggerLevel("INFO", 800);

    /**
     * The DEBUG log level designates fine-grained informational events that are most useful to debug an application.
     */
    public static final LoggerLevel DEBUG = new LoggerLevel("DEBUG", 500);

    /**
     * The TRACE log level designates finer-grained informational events than the DEBUG.
     */
    public static final LoggerLevel TRACE = new LoggerLevel("TRACE", 400);

    private static final Map<String, LoggerLevel> LEVELS;

    static {
        Map<String, LoggerLevel> levels = new HashMap<>() {{
            put("FATAL", FATAL);
            put("ERROR", ERROR);
            put("WARN", WARN);
            put("NOTICE", NOTICE);
            put("INFO", INFO);
            put("DEBUG", DEBUG);
            put("TRACE", TRACE);
        }};
        LEVELS = Collections.unmodifiableMap(levels);
    }

    /**
     * Constructs a new instance of LevelCup with the specified name and value.
     *
     * @param name  the name of the log level.
     * @param value the integer value of the log level.
     */
    protected LoggerLevel(String name, int value) {
        super(name, value);
    }

    /**
     * Parses the given name and returns the corresponding {@link LoggerLevel} instance.
     *
     * @param level the name of the log level to parse.
     * @return the corresponding {@link LoggerLevel} instance.
     */
    public static Level parse(String level) {
        if (LEVELS.containsKey(level)) {
            return LEVELS.get(level);
        } else {
            return Level.parse(level);
        }
    }

    /**
     * Returns a map of all available log levels.
     *
     * @return map of all available log levels.
     */
    public static Map<String, LoggerLevel> getLevels() {
        return LEVELS;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LoggerLevel) {
            return super.equals(o);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.intValue();
    }
}
