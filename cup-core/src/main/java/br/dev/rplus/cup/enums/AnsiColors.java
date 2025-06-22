package br.dev.rplus.cup.enums;

/**
 * AnsiColors is an enumeration that defines a set of ANSI escape codes for text color formatting.
 * These color codes are used to add color to terminal output, improving readability and visual appeal.
 */
public enum AnsiColors {

    /**
     * The ANSI escape code for resetting the text color to the default color.
     */
    RESET("\u001B[0m"),

    /**
     * The ANSI escape codes for light shade of the color black.
     */
    LIGHT_BLACK("\u001B[90m"),

    /**
     * The ANSI escape codes for dark shade of the color black.
     */
    BLACK("\u001B[30m"),

    /**
     * The ANSI escape codes for light shade of the color red.
     */
    LIGHT_RED("\u001B[91m"),

    /**
     * The ANSI escape codes for dark shade of the color red.
     */
    RED("\u001B[31m"),

    /**
     * The ANSI escape codes for light shade of the color green.
     */
    LIGHT_GREEN("\u001B[92m"),

    /**
     * The ANSI escape codes for dark shade of the color green.
     */
    GREEN("\u001B[32m"),

    /**
     * The ANSI escape codes for light shade of the color yellow.
     */
    LIGHT_YELLOW("\u001B[93m"),

    /**
     * The ANSI escape codes for dark shade of the color yellow.
     */
    YELLOW("\u001B[33m"),

    /**
     * The ANSI escape codes for light shade of the color blue.
     */
    LIGHT_BLUE("\u001B[94m"),

    /**
     * The ANSI escape codes for dark shade of the color blue.
     */
    BLUE("\u001B[34m"),

    /**
     * The ANSI escape codes for light shade of the color magenta.
     */
    LIGHT_MAGENTA("\u001B[95m"),

    /**
     * The ANSI escape codes for dark shade of the color magenta.
     */
    MAGENTA("\u001B[35m"),

    /**
     * The ANSI escape codes for light shade of the color cyan.
     */
    LIGHT_CYAN("\u001B[96m"),

    /**
     * The ANSI escape codes for dark shade of the color cyan.
     */
    CYAN("\u001B[36m"),

    /**
     * The ANSI escape codes for light shade of the color white.
     */
    LIGHT_WHITE("\u001B[97m"),

    /**
     * The ANSI escape codes for dark shade of the color white.
     */
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
        return this.value.asString();
    }
}
