package dev.rafandoo.cup.parsing;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Utility class for parsing values to different types.
 */
@UtilityClass
public final class TypeParser {

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
                    return Integer.parseInt(s);
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
                    return Double.parseDouble(s);
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
                    return Long.parseLong(s);
                } catch (NumberFormatException e) {
                    return 0L;
                }
            }
            default -> {
                return 0L;
            }
        }
    }

    /**
     * Parses the provided value to a float, if not possible returns 0.
     *
     * @param value the value to parse.
     * @return the parsed float value.
     */
    public static Float toFloat(Object value) {
        switch (value) {
            case Float f -> {
                return f;
            }
            case Double d -> {
                return d.floatValue();
            }
            case BigDecimal bd -> {
                return bd.floatValue();
            }
            case BigInteger bi -> {
                return bi.floatValue();
            }
            case Integer i -> {
                return i.floatValue();
            }
            case Long l -> {
                return l.floatValue();
            }
            case String s -> {
                try {
                    return Float.parseFloat(s);
                } catch (NumberFormatException e) {
                    return 0f;
                }
            }
            default -> {
                return 0f;
            }
        }
    }

    /**
     * Parses the provided value to a BigDecimal, if not possible returns {@link BigDecimal#ZERO}.
     *
     * @param value the value to parse.
     * @return the parsed BigDecimal value.
     */
    public static BigDecimal toBigDecimal(Object value) {
        switch (value) {
            case BigDecimal bd -> {
                return bd;
            }
            case BigInteger bi -> {
                return new BigDecimal(bi);
            }
            case Integer i -> {
                return BigDecimal.valueOf(i);
            }
            case Long l -> {
                return BigDecimal.valueOf(l);
            }
            case Double d -> {
                return BigDecimal.valueOf(d);
            }
            case Float f -> {
                return BigDecimal.valueOf(f);
            }
            case String s -> {
                try {
                    return new BigDecimal(s);
                } catch (NumberFormatException e) {
                    return BigDecimal.ZERO;
                }
            }
            default -> {
                return BigDecimal.ZERO;
            }
        }
    }

    /**
     * Parses the provided value to a BigInteger, if not possible returns {@link BigInteger#ZERO}.
     *
     * @param value the value to parse.
     * @return the parsed BigInteger value.
     */
    public static BigInteger toBigInteger(Object value) {
        switch (value) {
            case BigInteger bi -> {
                return bi;
            }
            case BigDecimal bd -> {
                return bd.toBigInteger();
            }
            case Integer i -> {
                return BigInteger.valueOf(i);
            }
            case Long l -> {
                return BigInteger.valueOf(l);
            }
            case Double d -> {
                return BigInteger.valueOf(d.longValue());
            }
            case Float f -> {
                return BigInteger.valueOf(f.longValue());
            }
            case String s -> {
                try {
                    return new BigInteger(s);
                } catch (NumberFormatException e) {
                    return BigInteger.ZERO;
                }
            }
            default -> {
                return BigInteger.ZERO;
            }
        }
    }

    /**
     * Parses the provided value to a boolean, if not possible returns false.
     * Strings "true", "yes", and "1" (case insensitive) are considered true.
     * Non-zero numbers are considered true.
     *
     * @param value the value to parse.
     * @return the parsed boolean value.
     */
    public static Boolean toBoolean(Object value) {
        switch (value) {
            case Boolean b -> {
                return b;
            }
            case String s -> {
                return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("yes") || s.equals("1");
            }
            case Number n -> {
                return n.intValue() != 0;
            }
            default -> {
                return false;
            }
        }
    }
}
