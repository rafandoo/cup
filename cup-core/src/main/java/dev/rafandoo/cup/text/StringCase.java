package dev.rafandoo.cup.text;

import lombok.experimental.UtilityClass;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Provides utility methods for converting strings between different letter cases.
 */
@UtilityClass
public final class StringCase {

    /**
     * Pattern that matches one or more whitespace characters.
     */
    private static final Pattern WHITESPACE = Pattern.compile("\\s+");

    /**
     * Pattern used to detect word boundaries in camelCase or PascalCase strings.
     */
    private static final Pattern CAMEL_CASE_BOUNDARY = Pattern.compile("(?<=[a-z0-9])(?=[A-Z])");

    /**
     * Converts a string to title case.
     * <p>
     * Example:
     * <pre>
     * "hello world java" -> "Hello World Java"
     * </pre>
     *
     * @param str the string to convert.
     * @return the string in title case, or the original string if null or blank.
     */
    public static String toTitleCase(String str) {
        if (StringValidator.isNullOrBlank(str)) {
            return str;
        }
        String[] words = WHITESPACE.split(str.trim());
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            result.append(capitalize(word)).append(' ');
        }

        return result.toString().trim();
    }


    /**
     * Capitalizes the first character of a string and lowercases the remaining characters.
     * <p>
     * Example:
     * <pre>
     * "jAVA" -> "Java"
     * </pre>
     *
     * @param str the string to capitalize.
     * @return the capitalized string, or the original string if null or blank.
     */
    public static String capitalize(String str) {
        if (StringValidator.isNullOrBlank(str)) {
            return str;
        }

        return str.substring(0, 1).toUpperCase(Locale.ROOT)
            + str.substring(1).toLowerCase(Locale.ROOT);
    }

    /**
     * Converts a string to camelCase.
     * <p>
     * Example:
     * <pre>
     * "hello world java" -> "helloWorldJava"
     * </pre>
     *
     * @param str the string to convert.
     * @return the string in camelCase, or the original string if null or blank.
     */
    public static String toCamelCase(String str) {
        if (StringValidator.isNullOrBlank(str)) {
            return str;
        }

        String[] parts = WHITESPACE.split(str.trim());
        StringBuilder result = new StringBuilder(parts[0].toLowerCase(Locale.ROOT));

        for (int i = 1; i < parts.length; i++) {
            result.append(capitalize(parts[i]));
        }

        return result.toString();
    }

    /**
     * Converts a string to PascalCase.
     * <p>
     * Example:
     * <pre>
     * "hello world java" -> "HelloWorldJava"
     * </pre>
     *
     * @param str the string to convert.
     * @return the string in PascalCase, or the original string if null or blank.
     */
    public static String toPascalCase(String str) {
        if (StringValidator.isNullOrBlank(str)) {
            return str;
        }

        String[] parts = WHITESPACE.split(str.trim());
        StringBuilder result = new StringBuilder();

        for (String part : parts) {
            result.append(capitalize(part));
        }

        return result.toString();
    }

    /**
     * Converts a string to snake_case.
     * <p>
     * Supports conversion from:
     * <ul>
     *     <li>space-separated words</li>
     *     <li>camelCase</li>
     *     <li>PascalCase</li>
     * </ul>
     * <p>
     * Example:
     * <pre>
     * "helloWorld Java" -> "hello_world_java"
     * </pre>
     *
     * @param str the string to convert.
     * @return the string in snake_case, or the original string if null or blank.
     */
    public static String toSnakeCase(String str) {
        if (StringValidator.isNullOrBlank(str)) {
            return str;
        }

        String withBoundaries = CAMEL_CASE_BOUNDARY.matcher(str).replaceAll("_");
        return WHITESPACE.matcher(withBoundaries.trim())
            .replaceAll("_")
            .toLowerCase(Locale.ROOT);
    }

    /**
     * Converts a string to kebab-case.
     * <p>
     * Example:
     * <pre>
     * "HelloWorld Java" -> "hello-world-java"
     * </pre>
     *
     * @param str the string to convert.
     * @return the string in kebab-case, or the original string if null or blank.
     */
    public static String toKebabCase(String str) {
        if (StringValidator.isNullOrBlank(str)) {
            return str;
        }

        String withBoundaries = CAMEL_CASE_BOUNDARY.matcher(str).replaceAll("-");
        return WHITESPACE.matcher(withBoundaries.trim())
            .replaceAll("-")
            .toLowerCase(Locale.ROOT);
    }
}
