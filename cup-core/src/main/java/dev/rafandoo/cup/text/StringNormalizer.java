package dev.rafandoo.cup.text;

import lombok.experimental.UtilityClass;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Provides utility methods for normalizing strings.
 * <p>
 * This class is intended for text normalization purposes only and
 * does not perform validation or formatting.
 */
@UtilityClass
public final class StringNormalizer {

    /**
     * Pattern that matches Unicode diacritical marks.
     */
    private static final Pattern DIACRITICAL_MARKS = Pattern.compile("\\p{M}");

    /**
     * Pattern that matches one or more whitespace characters.
     */
    private static final Pattern WHITESPACE = Pattern.compile("\\s+");

    /**
     * Pattern that matches non-ASCII characters.
     */
    private static final Pattern NON_ASCII = Pattern.compile("[^\\p{ASCII}]");

    /**
     * Removes accents (diacritical marks) from a string.
     * <p>
     * Example:
     * <pre>
     * "ação" -> "acao"
     * </pre>
     *
     * @param str the string to process.
     * @return the string without accents, or the original string if null or empty.
     */
    public static String removeAccents(String str) {
        if (StringValidator.isNullOrEmpty(str)) {
            return str;
        }

        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        return DIACRITICAL_MARKS.matcher(normalized).replaceAll("");
    }

    /**
     * Removes all non-ASCII characters from a string.
     *
     * @param str the string to process.
     * @return the string containing only ASCII characters, or the original string if null or empty.
     */
    public static String removeNonAscii(String str) {
        if (StringValidator.isNullOrEmpty(str)) {
            return str;
        }

        return NON_ASCII.matcher(str).replaceAll("");
    }

    /**
     * Removes accents and non-ASCII characters from a string.
     * <p>
     * This is a convenience method that combines
     * {@link #removeAccents(String)} and {@link #removeNonAscii(String)}.
     *
     * @param str the string to process.
     * @return the normalized string, or the original string if null or empty.
     */
    public static String removeAccentsAndSpecialCharacters(String str) {
        if (StringValidator.isNullOrEmpty(str)) {
            return str;
        }

        return removeNonAscii(removeAccents(str));
    }

    /**
     * Normalizes whitespace by trimming the string and replacing
     * consecutive whitespace characters with a single space.
     *
     * <p>
     * Example:
     * <pre>
     * "  hello   world  " -> "hello world"
     * </pre>
     *
     * @param str the string to process.
     * @return the whitespace-normalized string, or the original string if null or empty.
     */
    public static String normalizeWhitespace(String str) {
        if (StringValidator.isNullOrEmpty(str)) {
            return str;
        }

        return WHITESPACE.matcher(str.trim()).replaceAll(" ");
    }

    /**
     * Normalizes a string to a lowercase, accent-free, ASCII-only representation.
     * <p>
     * Useful for comparisons, indexing, and search operations.
     *
     * @param str the string to normalize.
     * @return a fully normalized string, or the original string if null or empty.
     */
    public static String normalizeForComparison(String str) {
        if (StringValidator.isNullOrEmpty(str)) {
            return str;
        }

        return normalizeWhitespace(
            removeAccentsAndSpecialCharacters(str)
                .toLowerCase(Locale.ROOT)
        );
    }

    /**
     * Generates a normalized identifier (slug) from a string.
     * <p>
     * Example:
     * <pre>
     * "Olá Mundo Java!" -> "ola-mundo-java"
     * </pre>
     *
     * @param str       the string to normalize.
     * @param delimiter the delimiter to use between words.
     * @return a normalized identifier, or null if the input is null.
     */
    public static String toSlug(String str, String delimiter) {
        if (str == null) {
            return null;
        }

        String normalized = normalizeForComparison(str);
        normalized = normalized.replaceAll("[^a-z0-9]+", delimiter);
        return normalized.replaceAll(
            "^" + Pattern.quote(delimiter) + "+|" + Pattern.quote(delimiter) + "+$",
            ""
        );
    }
}
