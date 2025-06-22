package br.dev.rplus.cup.enums;

public enum OperatingSystemEnum {

    WINDOWS("windows"),
    MAC("mac"),
    LINUX("linux"),
    CHROME_OS("chromeos"),

    PROP_OS_NAME("os.name"),
    PROP_OS_ARCH("os.arch"),
    PROP_OS_VERSION("os.version");

    private final TypedValue value;

    OperatingSystemEnum(Object value) {
        this.value = new TypedValue(value);
    }

    public TypedValue get() {
        return value;
    }
}
