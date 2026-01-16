package dev.rafandoo.cup.text;

import lombok.experimental.UtilityClass;

/**
 * Provides utility methods for validating strings.
 * <p>
 * This class contains common validation checks such as nullability,
 * emptiness, numeric validation, and pattern matching.
 */
@UtilityClass
public final class StringValidator {

    /**
     * Checks whether a string is {@code null} or empty.
     *
     * @param str the string to check.
     * @return {@code true} if the string is {@code null} or empty, {@code false} otherwise.
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Checks whether a string is {@code null}, empty, or contains only whitespace characters.
     *
     * @param str the string to check.
     * @return {@code true} if the string is {@code null}, empty, or blank, {@code false} otherwise.
     */
    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Checks whether a string is not {@code null} and not empty.
     *
     * @param str the string to check.
     * @return {@code true} if the string is not {@code null} and not empty.
     */
    public static boolean isNotEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    /**
     * Checks whether a string is not {@code null} and not blank.
     *
     * @param str the string to check.
     * @return {@code true} if the string is not {@code null} and contains non-whitespace characters.
     */
    public static boolean isNotBlank(String str) {
        return !isNullOrBlank(str);
    }

    /**
     * Checks whether a string contains only digits (0-9).
     * <p>
     * This method returns {@code false} for {@code null} or empty strings.
     *
     * @param str the string to check.
     * @return {@code true} if the string contains only digits, {@code false} otherwise.
     */
    public static boolean isNumeric(String str) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        return str.chars().allMatch(Character::isDigit);
    }

    /**
     * Checks whether a string has a length within a specified range.
     *
     * @param str the string to check.
     * @param min minimum allowed length (inclusive).
     * @param max maximum allowed length (inclusive).
     * @return {@code true} if the string length is within the range.
     */
    public static boolean hasLengthBetween(String str, int min, int max) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        return length >= min && length <= max;
    }
}
