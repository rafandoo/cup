package dev.rafandoo.cup.enums;

/**
 * Enum representing known operating systems and system properties
 * used for OS detection and system information retrieval.
 */
public enum OperatingSystemEnum {

    /**
     * Represents the Windows operating system.
     */
    WINDOWS("windows"),

    /**
     * Represents the macOS operating system.
     */
    MAC("mac"),

    /**
     * Represents the Linux operating system.
     */
    LINUX("linux"),

    /**
     * Represents the Chrome OS operating system.
     */
    CHROME_OS("chromeos"),

    /**
     * System property key for retrieving the OS name: {@code os.name}.
     */
    PROP_OS_NAME("os.name"),

    /**
     * System property key for retrieving the OS architecture: {@code os.arch}.
     */
    PROP_OS_ARCH("os.arch"),

    /**
     * System property key for retrieving the OS version: {@code os.version}.
     */
    PROP_OS_VERSION("os.version");

    private final TypedValue value;

    /**
     * Constructs an enum constant with the given value.
     *
     * @param value the underlying string value representing the OS or system property.
     */
    OperatingSystemEnum(Object value) {
        this.value = new TypedValue(value);
    }

    /**
     * Returns the {@link TypedValue} associated with this enum constant.
     *
     * @return the wrapped value.
     */
    public TypedValue get() {
        return value;
    }
}
