package br.dev.rplus.cup.os;

import br.dev.rplus.cup.enums.OperatingSystemEnum;
import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("unused")
public final class OperatingSystem {

    private static final String OS_NAME = System.getProperty(OperatingSystemEnum.PROP_OS_NAME.get().asString()).toLowerCase();
    private static final String OS_ARCH = System.getProperty(OperatingSystemEnum.PROP_OS_ARCH.get().asString()).toLowerCase();

    private static boolean isOS(OperatingSystemEnum os) {
        return OS_NAME.contains(os.get().asString());
    }

    public static boolean isWindows() {
        return isOS(OperatingSystemEnum.WINDOWS);
    }

    public static boolean isLinux() {
        return isOS(OperatingSystemEnum.LINUX);
    }

    public static boolean isMac() {
        return isOS(OperatingSystemEnum.MAC);
    }

    public static boolean isChromeOs() {
        return isOS(OperatingSystemEnum.CHROME_OS);
    }

    public static boolean isUnix() {
        return isLinux() || isMac();
    }

    public static boolean isX64() {
        return System.getProperty("os.arch").toLowerCase().contains("64");
    }

    public static boolean isX86() {
        return !isX64();
    }

    public static boolean isArm() {
        return OS_ARCH.contains("arm") || OS_ARCH.contains("aarch");
    }

    public static boolean isWindowsSubsystemForLinux() {
        return OS_NAME.contains("linux") && System.getenv("WSL_DISTRO_NAME") != null;
    }

    public static String describe() {
        return String.format("OS: %s, Arch: %s, Version: %s", getOsName(), getOsArch(), getOsVersion());
    }

    public static String getOsName() {
        return OS_NAME;
    }

    public static String getOsArch() {
        return OS_ARCH;
    }

    public static String getOsVersion() {
        return System.getProperty(OperatingSystemEnum.PROP_OS_VERSION.get().asString());
    }

}
