package br.dev.rplus.cup.utils;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Pattern;

/**
 * Utility class for common operations on strings, including manipulation, conversion, and validation.
 */
public class StringUtils {

    /**
     * Checks if a string is null or empty.
     *
     * @param str the string to check.
     * @return true if the string is null or empty, false otherwise.
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Checks if a string is null, empty, or only contains whitespace.
     *
     * @param str the string to check.
     * @return true if the string is null, empty, or contains only whitespace, false otherwise.
     */
    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Capitalizes the first letter of a string.
     *
     * @param str the string to capitalize.
     * @return the string with the first letter capitalized.
     */
    public static String capitalize(String str) {
        if (isNullOrBlank(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * Converts a string to camel case.
     *
     * @param str the string to convert.
     * @return the string in camel case.
     */
    public static String toCamelCase(String str) {
        if (isNullOrBlank(str)) {
            return str;
        }
        String[] parts = str.split(" ");
        StringBuilder camelCaseString = new StringBuilder(parts[0].toLowerCase());
        for (int i = 1; i < parts.length; i++) {
            camelCaseString.append(capitalize(parts[i]));
        }
        return camelCaseString.toString();
    }

    /**
     * Converts a string to snake case.
     *
     * @param str the string to convert.
     * @return the string in snake case.
     */
    public static String toSnakeCase(String str) {
        if (isNullOrBlank(str)) {
            return str;
        }
        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase().replace(" ", "_");
    }

    /**
     * Reverses the characters in a string.
     *
     * @param str the string to reverse.
     * @return the reversed string.
     */
    public static String reverse(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * Checks if a string contains only digits.
     *
     * @param str the string to check.
     * @return true if the string contains only digits, false otherwise.
     */
    public static boolean isNumeric(String str) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        return str.chars().allMatch(Character::isDigit);
    }

    /**
     * Counts the number of occurrences of a substring in a string.
     *
     * @param str       the string to search.
     * @param substring the substring to count.
     * @return the number of occurrences of the substring.
     */
    public static int countOccurrences(String str, String substring) {
        if (isNullOrEmpty(str) || isNullOrEmpty(substring)) {
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
     * Trims whitespace from the beginning and end of a string.
     *
     * @param str the string to trim.
     * @return the trimmed string.
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * Joins an array of strings into a single string with a specified delimiter.
     *
     * @param delimiter the delimiter to use.
     * @param strings   the array of strings to join.
     * @return the joined string.
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
            sb.append(strings[i]);
        }
        return sb.toString();
    }

    /**
     * Pads a string to a specified length with a specified character.
     *
     * @param str        the string to pad.
     * @param length     the desired length.
     * @param padChar    the character to use for padding.
     * @param padLeft    true to pad on the left, false to pad on the right.
     * @return the padded string.
     */
    public static String pad(String str, int length, char padChar, boolean padLeft) {
        if (str == null) {
            str = "";
        }
        StringBuilder sb = new StringBuilder(str);
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
     * Converts a string to title case.
     *
     * @param str the string to convert.
     * @return the string in title case.
     */
    public static String toTitleCase(String str) {
        if (isNullOrBlank(str)) {
            return str;
        }
        String[] words = str.split(" ");
        StringBuilder titleCase = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                titleCase.append(capitalize(word)).append(" ");
            }
        }
        return titleCase.toString().trim();
    }

    /**
     * Removes accents and special characters from a string.
     *
     * @param str the string to process.
     * @return the string with accents and special characters removed.
     */
    public static String removeAccentsAndSpecialCharacters(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        String normalized = Normalizer.normalize(str, Form.NFD);
        Pattern pattern = Pattern.compile("\\p{M}"); // Diacritical marks.
        return pattern.matcher(normalized).replaceAll("").replaceAll("[^\\p{ASCII}]", "");
    }
}
