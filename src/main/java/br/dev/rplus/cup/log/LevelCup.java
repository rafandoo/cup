package br.dev.rplus.cup.log;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * LevelCup is a custom implementation of the {@link Level} class, providing additional log levels
 * beyond the standard levels provided by the Java logging framework.
 * It includes custom levels such as DEBUG, NOTICE, and CRITICAL with specific values.
 */
public class LevelCup extends Level {

    /** Log level for all messages. */
    public static final Level ALL = new LevelCup("ALL", Integer.MIN_VALUE);
    /** Log level for debugging messages. */
    public static final Level DEBUG = new LevelCup("DEBUG", 500);
    /** Log level for informational messages. */
    public static final Level INFO = new LevelCup("INFO", 800);
    /** Log level for notice messages. */
    public static final Level NOTICE = new LevelCup("NOTICE", 850);
    /** Log level for warning messages. */
    public static final Level WARNING = new LevelCup("WARNING", 900);
    /** Log level for error messages. */
    public static final Level ERROR = new LevelCup("ERROR", 950);
    /** Log level for critical messages. */
    public static final Level CRITICAL = new LevelCup("CRITICAL", 1000);
    /** Log level to turn off logging. */
    public static final Level OFF = new LevelCup("OFF", Integer.MAX_VALUE);

    private static final Map<String, Level> LEVELS;

    static {
        Map<String, Level> levels = new HashMap<>() {{
            put(ALL.getName(), ALL);
            put(DEBUG.getName(), DEBUG);
            put(INFO.getName(), INFO);
            put(NOTICE.getName(), NOTICE);
            put(WARNING.getName(), WARNING);
            put(ERROR.getName(), ERROR);
            put(CRITICAL.getName(), CRITICAL);
            put(OFF.getName(), OFF);
        }};

        LEVELS = Collections.unmodifiableMap(levels);
    }

    /**
     * Constructs a new instance of LevelCup with the specified name and value.
     *
     * @param name The name of the log level.
     * @param value The integer value of the log level.
     */
    protected LevelCup(String name, int value) {
        super(name, value);
    }

    /**
     * Parses the given name and returns the corresponding {@link LevelCup} instance.
     *
     * @param name The name of the log level to parse.
     * @return The corresponding {@link LevelCup} instance.
     * @throws IllegalArgumentException If the name does not match any known log level.
     */
    public static Level parse(String name) {
        Level level = LEVELS.get(name.toUpperCase());
        if (level == null) {
            throw new IllegalArgumentException("Unknown log level: " + name);
        }
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LevelCup) {
            return super.equals(o);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.intValue();
    }
}