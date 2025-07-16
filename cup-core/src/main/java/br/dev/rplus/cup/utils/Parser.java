package br.dev.rplus.cup.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Utility class for parsing values to different types.
 */
@UtilityClass
public class Parser {

    /**
     * Parses the provided value to an integer, if not possible returns 0.
     *
     * @param value the value to parse.
     * @return the parsed integer value.
     */
    public static Integer toInt(Object value) {
        switch (value) {
            case Integer i -> {
                return i;
            }
            case BigInteger bi -> {
                return bi.intValue();
            }
            case BigDecimal bd -> {
                return bd.intValue();
            }
            case Long l -> {
                return l.intValue();
            }
            case Double d -> {
                return d.intValue();
            }
            case Float f -> {
                return f.intValue();
            }
            case String s -> {
                try {
                    return Integer.parseInt((String) value);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
            default -> {
                return 0;
            }
        }
    }

    /**
     * Parses the provided value to a double, if not possible returns 0.
     *
     * @param value the value to parse.
     * @return the parsed double value.
     */
    public static Double toDouble(Object value) {
        switch (value) {
            case Double d -> {
                return d;
            }
            case BigDecimal bd -> {
                return bd.doubleValue();
            }
            case BigInteger bi -> {
                return bi.doubleValue();
            }
            case Integer i -> {
                return i.doubleValue();
            }
            case Long l -> {
                return l.doubleValue();
            }
            case Float f -> {
                return f.doubleValue();
            }
            case String s -> {
                try {
                    return Double.parseDouble((String) value);
                } catch (NumberFormatException e) {
                    return (double) 0;
                }
            }
            default -> {
                return (double) 0;
            }
        }
    }

    /**
     * Parses the provided value to a long, if not possible returns 0.
     *
     * @param value the value to parse.
     * @return the parsed long value.
     */
    public static Long toLong(Object value) {
        switch (value) {
            case Long l -> {
                return l;
            }
            case BigInteger bi -> {
                return bi.longValue();
            }
            case BigDecimal bd -> {
                return bd.longValue();
            }
            case Integer i -> {
                return i.longValue();
            }
            case Double d -> {
                return d.longValue();
            }
            case Float f -> {
                return f.longValue();
            }
            case String s -> {
                try {
                    return Long.parseLong((String) value);
                } catch (NumberFormatException e) {
                    return 0L;
                }
            }
            default -> {
                return 0L;
            }
        }
    }
}
