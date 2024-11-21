package br.dev.rplus.cup.enums;

/**
 * AnsiColors is an enumeration that defines a set of ANSI escape codes for text color formatting.
 * These color codes are used to add color to terminal output, improving readability and visual appeal.
 */
public enum AnsiColors {

    RESET("\u001B[0m"),

    LIGHT_BLACK("\u001B[90m"),
    BLACK("\u001B[30m"),

    LIGHT_RED("\u001B[91m"),
    RED("\u001B[31m"),

    LIGHT_GREEN("\u001B[92m"),
    GREEN("\u001B[32m"),

    LIGHT_YELLOW("\u001B[93m"),
    YELLOW("\u001B[33m"),

    LIGHT_BLUE("\u001B[94m"),
    BLUE("\u001B[34m"),

    LIGHT_MAGENTA("\u001B[95m"),
    MAGENTA("\u001B[35m"),

    LIGHT_CYAN("\u001B[96m"),
    CYAN("\u001B[36m"),

    LIGHT_WHITE("\u001B[97m"),
    WHITE("\u001B[37m");

    private final TypedValue value;

    /**
     * Constructs an instance of AnsiColors with the specified color code.
     *
     * @param value The ANSI escape code for the color.
     */
    AnsiColors(Object value) {
        this.value = new TypedValue(value);
    }

    /**
     * Gets the ANSI color code associated with this enum constant.
     *
     * @return The ANSI color code as a string.
     */
    public String getColorCode() {
        return value.getTypedValue(String.class);
    }
}
