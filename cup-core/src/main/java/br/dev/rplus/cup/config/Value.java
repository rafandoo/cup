package br.dev.rplus.cup.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A flexible wrapper class that allows dynamic access and type-safe conversion
 * of an underlying value, typically used for parsed configuration entries.
 */
@SuppressWarnings("unused")
public class Value {

    private final Object value;

    /**
     * Constructs a new {@code Value} wrapping the provided object.
     *
     * @param value the object to wrap.
     */
    public Value(Object value) {
        this.value = value;
    }

    /**
     * Converts the value to a {@link String}.
     *
     * @return the value as a string, or {@code null} if the value is {@code null}.
     */
    public String asString() {
        return value != null ? value.toString() : null;
    }

    /**
     * Converts the value to an {@code int}.
     *
     * @return the value as an integer
     * @throws IllegalStateException if the value cannot be converted.
     */
    public int asInt() {
        if (value instanceof Number) return ((Number) value).intValue();
        if (value instanceof String) return Integer.parseInt((String) value);
        throw new IllegalStateException("Cannot convert to int: " + value);
    }

    /**
     * Converts the value to a {@code boolean}.
     *
     * @return the value as a boolean.
     * @throws IllegalStateException if the value cannot be converted.
     */
    public boolean asBoolean() {
        if (value instanceof Boolean) return (Boolean) value;
        if (value instanceof String) return Boolean.parseBoolean((String) value);
        throw new IllegalStateException("Cannot convert to boolean: " + value);
    }

    /**
     * Converts the value to a {@code double}.
     *
     * @return the value as a double.
     * @throws IllegalStateException if the value cannot be converted.
     */
    public double asDouble() {
        if (value instanceof Number) return ((Number) value).doubleValue();
        if (value instanceof String) return Double.parseDouble((String) value);
        throw new IllegalStateException("Cannot convert to double: " + value);
    }

    /**
     * Converts the value to a {@link List} of {@code Value} elements.
     *
     * @return the value as a list of {@code Value}.
     * @throws IllegalStateException if the value is not a list.
     */
    public List<Value> asList() {
        if (value instanceof List<?> list) {
            List<Value> result = new ArrayList<>();
            for (Object item : list) {
                result.add(new Value(item));
            }
            return result;
        }
        throw new IllegalStateException("Value is not a list: " + value);
    }

    /**
     * Converts the value to a {@link Map} with {@code String} keys and {@code Value} values.
     *
     * @return the value as a map.
     * @throws IllegalStateException if the value is not a map or contains non-string keys.
     */
    public Map<String, Value> asMap() {
        if (value instanceof Map<?, ?> map) {
            Map<String, Value> result = new LinkedHashMap<>();
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (!(entry.getKey() instanceof String)) {
                    throw new IllegalStateException("Map contains non-string key: " + entry.getKey());
                }
                result.put((String) entry.getKey(), new Value(entry.getValue()));
            }
            return result;
        }
        throw new IllegalStateException("Value is not a map: " + value);
    }

    /**
     * Checks if the underlying value is {@code null}.
     *
     * @return {@code true} if the value is {@code null}, {@code false} otherwise.
     */
    public boolean isNull() {
        return value == null;
    }

    /**
     * Returns the raw wrapped object.
     *
     * @return the original object value.
     */
    public Object raw() {
        return value;
    }
}
