package br.dev.rplus.cup.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.Map;

/**
 * Utility class for working with JSON data. Provides methods to convert between JSON strings
 * and Java objects, as well as to validate and format JSON data.
 */
@SuppressWarnings("unused")
public class Json {

    /**
     * Private constructor to prevent instantiation.
     */
    private Json() {}

    /**
     * Converts a {@link Map} to a JSON string.
     *
     * @param map the {@link Map} to convert.
     * @return the JSON string representation of the {@link Map}.
     */
    public static String mapToJson(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toString();
    }

    /**
     * Converts a JSON string to a {@link Map}.
     *
     * @param json the JSON string to convert.
     * @return the {@link Map} representation of the JSON.
     */
    public static Map<String, Object> jsonToMap(String json) {
        JSONObject jsonObject = new JSONObject(json);
        return jsonObject.toMap();
    }

    /**
     * Converts a Java object to a JSON string.
     * <p>
     * The object can be a {@link Map} or an {@link Iterable}. Other types are not supported.
     * </p>
     *
     * @param object the Java object to convert (must be of a type supported by {@link JSONObject} or {@link JSONArray}).
     * @return the JSON string representation of the object.
     * @throws JSONException if the object cannot be converted to JSON.
     */
    public static String toJson(Object object) throws JSONException {
        if (object instanceof Map) {
            return new JSONObject((Map<?, ?>) object).toString();
        } else if (object instanceof Iterable) {
            return new JSONArray((Iterable<?>) object).toString();
        } else {
            throw new JSONException("Unsupported object type");
        }
    }

    /**
     * Converts a JSON string to a {@link JSONObject}.
     *
     * @param json the JSON string to convert.
     * @return the {@link JSONObject} representation of the JSON string.
     * @throws JSONException if the JSON is invalid.
     */
    public static JSONObject parseJsonObject(String json) throws JSONException {
        return new JSONObject(json);
    }

    /**
     * Converts a JSON string to a {@link JSONArray}.
     *
     * @param json the JSON string to convert.
     * @return the {@link JSONArray} representation of the JSON string.
     * @throws JSONException if the JSON is invalid.
     */
    public static JSONArray parseJsonArray(String json) throws JSONException {
        return new JSONArray(json);
    }

    /**
     * Pretty prints a JSON string with indentation.
     *
     * @param json the JSON string to pretty print.
     * @return the pretty-printed JSON string.
     * @throws JSONException if the JSON is invalid.
     */
    public static String prettyPrintJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        return jsonObject.toString(4); // 4 is the number of spaces for indentation
    }

    /**
     * Checks if a string is a valid JSON.
     * <p>
     * The string is considered valid if it can be parsed as either a {@link JSONObject} or a {@link JSONArray}.
     * </p>
     *
     * @param json the string to check.
     * @return {@code true} if the string is a valid JSON, {@code false} otherwise.
     */
    public static boolean isValidJson(String json) {
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
}
