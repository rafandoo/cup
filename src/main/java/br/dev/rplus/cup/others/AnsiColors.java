package br.dev.rplus.cup.others;

/**
 * AnsiColors is an enumeration that defines a set of ANSI escape codes for text color formatting.
 * These color codes are used to add color to terminal output, improving readability and visual appeal.
 */
public enum AnsiColors {

    RESET("\u001B[0m"),  // Reset color

    LIGHT_BLACK("\u001B[90m"), // Light black color
    BLACK("\u001B[30m"),       // Black color

    LIGHT_RED("\u001B[91m"),   // Light red color
    RED("\u001B[31m"),         // Red color

    LIGHT_GREEN("\u001B[92m"), // Light green color
    GREEN("\u001B[32m"),       // Green color

    LIGHT_YELLOW("\u001B[93m"), // Light yellow color
    YELLOW("\u001B[33m"),       // Yellow color

    LIGHT_BLUE("\u001B[94m"),   // Light blue color
    BLUE("\u001B[34m"),         // Blue color

    LIGHT_MAGENTA("\u001B[95m"), // Light magenta color
    MAGENTA("\u001B[35m"),       // Magenta color

    LIGHT_CYAN("\u001B[96m"),   // Light cyan color
    CYAN("\u001B[36m"),         // Cyan color

    LIGHT_WHITE("\u001B[97m"),  // Light white color
    WHITE("\u001B[37m");        // White color

    private final String colorCode;

    /**
     * Constructs an instance of AnsiColors with the specified color code.
     *
     * @param colorCode The ANSI escape code for the color.
     */
    AnsiColors(String colorCode) {
        this.colorCode = colorCode;
    }

    /**
     * Gets the ANSI color code associated with this enum constant.
     *
     * @return The ANSI color code as a string.
     */
    public String getColorCode() {
        return colorCode;
    }
}
