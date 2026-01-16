package dev.rafandoo.cup.text;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Objects;

/**
 * Provides common operations on {@link String} values.
 * <p>
 * This class contains generic string manipulation utilities that do not fit
 * into validation, normalization, or case conversion concerns.
 */
@UtilityClass
public final class Strings {

    /**
     * Reverses the characters of a string.
     *
     * @param str the string to reverse.
     * @return the reversed string, or the original string if null or empty.
     */
    public static String reverse(String str) {
        if (StringValidator.isNullOrEmpty(str)) {
            return str;
        }
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * Counts the number of occurrences of a substring in a string.
     * <p>
     * Overlapping occurrences are counted.
     *
     * <pre>
     * countOccurrences("aaaa", "aa") -> 2
     * </pre>
     *
     * @param str       the string to search.
     * @param substring the substring to count.
     * @return the number of occurrences found.
     */
    public static int countOccurrences(String str, String substring) {
        if (StringValidator.isNullOrEmpty(str) || StringValidator.isNullOrEmpty(substring)) {
            return 0;
        }

        int count = 0;
        int index = 0;

        while ((index = str.indexOf(substring, index)) != -1) {
            count++;
            index += substring.length();
        }

        return count;
    }

    /**
     * Joins an array of strings using a delimiter.
     * <p>
     * {@code null} values inside the array are converted to empty strings.
     *
     * @param delimiter the delimiter to use.
     * @param strings   the strings to join.
     * @return the joined string, or an empty string if the array is null or empty.
     */
    public static String join(String delimiter, String... strings) {
        if (strings == null || strings.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < strings.length; i++) {
            if (i > 0) {
                sb.append(delimiter);
            }
            sb.append(Objects.toString(strings[i], ""));
        }

        return sb.toString();
    }

    /**
     * Joins a collection of strings using a delimiter.
     *
     * @param delimiter the delimiter to use.
     * @param strings   the collection of strings to join.
     * @return the joined string, or an empty string if the collection is null or empty.
     */
    public static String join(String delimiter, Collection<String> strings) {
        if (strings == null || strings.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (String str : strings) {
            if (!first) {
                sb.append(delimiter);
            }
            sb.append(Objects.toString(str, ""));
            first = false;
        }

        return sb.toString();
    }

    /**
     * Pads a string to a specified length using a given character.
     *
     * @param str     the string to pad (null is treated as empty).
     * @param length  the desired minimum length.
     * @param padChar the character used for padding.
     * @param padLeft {@code true} to pad on the left, {@code false} to pad on the right.
     * @return the padded string.
     */
    public static String pad(String str, int length, char padChar, boolean padLeft) {
        String value = Objects.toString(str, "");
        if (value.length() >= length) {
            return value;
        }

        StringBuilder sb = new StringBuilder(value);
        while (sb.length() < length) {
            if (padLeft) {
                sb.insert(0, padChar);
            } else {
                sb.append(padChar);
            }
        }
        return sb.toString();
    }

    /**
     * Pads a string on the left.
     *
     * @param str     the string to pad.
     * @param length  the desired minimum length.
     * @param padChar the character used for padding.
     * @return the left-padded string.
     */
    public static String padLeft(String str, int length, char padChar) {
        return pad(str, length, padChar, true);
    }

    /**
     * Pads a string on the right.
     *
     * @param str     the string to pad.
     * @param length  the desired minimum length.
     * @param padChar the character used for padding.
     * @return the right-padded string.
     */
    public static String padRight(String str, int length, char padChar) {
        return pad(str, length, padChar, false);
    }

    /**
     * Returns the first {@code maxLength} characters of a string.
     *
     * @param str       the string to truncate.
     * @param maxLength the maximum length.
     * @return the truncated string, or the original string if shorter.
     */
    public static String truncate(String str, int maxLength) {
        if (str == null || maxLength < 0 || str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength);
    }

    /**
     * Safely compares two strings for equality.
     *
     * @param a first string.
     * @param b second string.
     * @return {@code true} if both strings are equal or both null.
     */
    public static boolean equals(String a, String b) {
        return Objects.equals(a, b);
    }

}
