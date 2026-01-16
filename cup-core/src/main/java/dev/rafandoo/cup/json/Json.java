package dev.rafandoo.cup.json;

import dev.rafandoo.cup.text.StringValidator;
import lombok.experimental.UtilityClass;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Utility class for JSON operations.
 */
@UtilityClass
public final class Json {

    /**
     * Converts a {@link Map} into its JSON string representation.
     *
     * @param map the map to convert.
     * @return the JSON string, or {@code null} if the map is {@code null}.
     */
    public static String toJson(Map<String, ?> map) {
        if (map == null) return null;
        return new JSONObject(map).toString();
    }

    /**
     * Converts an {@link Iterable} into its JSON array string representation.
     *
     * @param iterable the iterable to convert.
     * @return the JSON string, or {@code null} if the iterable is {@code null}.
     */
    public static String toJson(Iterable<?> iterable) {
        if (iterable == null) return null;
        return new JSONArray(iterable).toString();
    }

    /**
     * Converts a supported Java object into a JSON string.
     * <p>
     * Supported types:
     * <ul>
     *   <li>{@link Map}</li>
     *   <li>{@link Iterable}</li>
     * </ul>
     * </p>
     *
     * @param value the object to convert.
     * @return the JSON string, or {@code null} if the value is {@code null}.
     * @throws IllegalArgumentException if the object type is not supported.
     */
    @SuppressWarnings("unchecked")
    public static String toJson(Object value) {
        return switch (value) {
            case null -> null;
            case Map<?, ?> map -> toJson((Map<String, ?>) map);
            case Iterable<?> iterable -> toJson(iterable);
            default -> throw new IllegalArgumentException(
                "Unsupported type for JSON serialization: " + value.getClass()
            );
        };

    }

    /**
     * Converts a JSON object string into a {@link Map}.
     *
     * @param json the JSON string to convert.
     * @return a map representation of the JSON, or {@code null} if the input is null or empty.
     * @throws IllegalArgumentException if the JSON is not a valid object.
     */
    public static Map<String, Object> toMap(String json) {
        if (StringValidator.isNullOrEmpty(json)) return null;
        return new JSONObject(json).toMap();
    }

    /**
     * Converts a JSON array string into a {@link List}.
     *
     * @param json the JSON string to convert.
     * @return a list representation of the JSON array, or {@code null} if the input is null or empty.
     * @throws IllegalArgumentException if the JSON is not a valid array.
     */
    public static List<Object> toList(String json) {
        if (StringValidator.isNullOrEmpty(json)) return null;
        return new JSONArray(json).toList();
    }

    /**
     * Checks whether a string is a valid JSON.
     * <p>
     * A JSON is considered valid if it can be parsed as either
     * a JSON object or a JSON array.
     * </p>
     *
     * @param json the string to validate.
     * @return {@code true} if the string is valid JSON, {@code false} otherwise.
     */
    public static boolean isValid(String json) {
        if (StringValidator.isNullOrEmpty(json)) return false;

        try {
            new JSONObject(json);
            return true;
        } catch (JSONException ignored) {
            try {
                new JSONArray(json);
                return true;
            } catch (JSONException ignoredEx) {
                return false;
            }
        }
    }

    /**
     * Pretty prints a JSON string using indentation.
     *
     * @param json the JSON string to format.
     * @return the formatted JSON string.
     * @throws IllegalArgumentException if the JSON is invalid.
     */
    public static String prettyPrint(String json) {
        if (!isValid(json)) {
            throw new IllegalArgumentException("Invalid JSON");
        }

        try {
            return new JSONObject(json).toString(4);
        } catch (JSONException ignored) {
            return new JSONArray(json).toString(4);
        }
    }
}
