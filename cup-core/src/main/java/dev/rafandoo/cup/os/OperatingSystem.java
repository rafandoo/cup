package dev.rafandoo.cup.os;

import lombok.experimental.UtilityClass;

/**
 * Utility class for detecting and interacting with the operating system properties of the host environment.
 */
@UtilityClass
public final class OperatingSystem {

    /**
     * The name of the operating system, in lowercase.
     */
    private static final String OS_NAME = System.getProperty(OperatingSystemType.PROP_OS_NAME.get().asString()).toLowerCase();

    /**
     * The architecture of the operating system, in lowercase.
     */
    private static final String OS_ARCH = System.getProperty(OperatingSystemType.PROP_OS_ARCH.get().asString()).toLowerCase();

    /**
     * Checks if the current OS matches the given {@link OperatingSystemType} value.
     *
     * @param os the OS to compare against.
     * @return true if the current OS name contains the specified OS string, false otherwise.
     */
    private static boolean isOS(OperatingSystemType os) {
        return OS_NAME.contains(os.get().asString());
    }

    /**
     * Checks if the current OS is Windows.
     *
     * @return true if Windows, false otherwise.
     */
    public static boolean isWindows() {
        return isOS(OperatingSystemType.WINDOWS);
    }

    /**
     * Checks if the current OS is Linux.
     *
     * @return true if Linux, false otherwise.
     */
    public static boolean isLinux() {
        return isOS(OperatingSystemType.LINUX);
    }

    /**
     * Checks if the current OS is macOS.
     *
     * @return true if macOS, false otherwise.
     */
    public static boolean isMac() {
        return isOS(OperatingSystemType.MAC);
    }

    /**
     * Checks if the current OS is Chrome OS.
     *
     * @return true if Chrome OS, false otherwise.
     */
    public static boolean isChromeOs() {
        return isOS(OperatingSystemType.CHROME_OS);
    }

    /**
     * Checks if the current OS is a Unix-based system (Linux or macOS).
     *
     * @return true if Unix-based, false otherwise.
     */
    public static boolean isUnix() {
        return isLinux() || isMac();
    }

    /**
     * Checks if the system architecture is 64-bit.
     *
     * @return true if x64, false otherwise.
     */
    public static boolean isX64() {
        return System.getProperty("os.arch").toLowerCase().contains("64");
    }

    /**
     * Checks if the system architecture is 32-bit.
     *
     * @return true if x86 (i.e., not x64), false otherwise.
     */
    public static boolean isX86() {
        return !isX64();
    }

    /**
     * Checks if the system architecture is ARM or AARCH.
     *
     * @return true if ARM-based architecture, false otherwise.
     */
    public static boolean isArm() {
        return OS_ARCH.contains("arm") || OS_ARCH.contains("aarch");
    }

    /**
     * Checks if the current environment is WSL (Windows Subsystem for Linux).
     *
     * @return true if running under WSL, false otherwise.
     */
    public static boolean isWindowsSubsystemForLinux() {
        return OS_NAME.contains("linux") && System.getenv("WSL_DISTRO_NAME") != null;
    }

    /**
     * Returns a formatted string describing the current OS name, architecture, and version.
     *
     * @return a string with OS information.
     */
    public static String describe() {
        return String.format("OS: %s, Arch: %s, Version: %s", getOsName(), getOsArch(), getOsVersion());
    }

    /**
     * Returns the name of the current operating system.
     *
     * @return the OS name.
     */
    public static String getOsName() {
        return OS_NAME;
    }

    /**
     * Returns the architecture of the current operating system.
     *
     * @return the OS architecture.
     */
    public static String getOsArch() {
        return OS_ARCH;
    }

    /**
     * Returns the version of the current operating system.
     *
     * @return the OS version.
     */
    public static String getOsVersion() {
        return System.getProperty(OperatingSystemType.PROP_OS_VERSION.get().asString());
    }
}
