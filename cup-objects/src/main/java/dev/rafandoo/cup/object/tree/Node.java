package dev.rafandoo.cup.object.tree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A flexible wrapper class that allows dynamic access and type-safe conversion
 * of an underlying value, typically used for parsed configuration entries.
 */
public class Node {

    private final Object value;

    /**
     * Constructs a new {@code Node} wrapping the provided value.
     *
     * @param value the raw value represented by this node.
     */
    public Node(Object value) {
        this.value = value;
    }

    /**
     * Converts the underlying value to a {@link String}.
     *
     * @return the value as a string, or {@code null} if the value is {@code null}.
     */
    public String asString() {
        return !this.isNull() ? value.toString() : null;
    }

    /**
     * Converts the underlying value to an {@code int}.
     *
     * @return the value as an integer.
     * @throws IllegalStateException if the value cannot be converted to an integer.
     */
    public int asInt() {
        if (this.value instanceof Number) return ((Number) this.value).intValue();
        if (this.value instanceof String) return Integer.parseInt((String) this.value);
        throw new IllegalStateException("Cannot convert to int: " + this.value);
    }

    /**
     * Converts the underlying value to a {@code boolean}.
     *
     * @return the value as a boolean.
     * @throws IllegalStateException if the value cannot be converted to a boolean.
     */
    public boolean asBoolean() {
        if (this.value instanceof Boolean) return (Boolean) this.value;
        if (this.value instanceof String) return Boolean.parseBoolean((String) this.value);
        throw new IllegalStateException("Cannot convert to boolean: " + this.value);
    }

    /**
     * Converts the underlying value to a {@code double}.
     *
     * @return the value as a double.
     * @throws IllegalStateException if the value cannot be converted to a double.
     */
    public double asDouble() {
        if (this.value instanceof Number) return ((Number) this.value).doubleValue();
        if (this.value instanceof String) return Double.parseDouble((String) this.value);
        throw new IllegalStateException("Cannot convert to double: " + this.value);
    }

    /**
     * Converts the underlying value to a {@link List} of {@link Node}s.
     * <p>
     * Each element in the list is wrapped in a new {@code Node}.
     *
     * @return the value as a list of nodes.
     * @throws IllegalStateException if the value is not a list.
     */
    public List<Node> asList() {
        if (this.value instanceof List<?> list) {
            List<Node> result = new ArrayList<>();
            for (Object item : list) {
                result.add(new Node(item));
            }
            return result;
        }
        throw new IllegalStateException("Value is not a list: " + this.value);
    }

    /**
     * Converts the underlying value to a {@link Map} with {@link String} keys
     * and {@link Node} values.
     * <p>
     * Each entry in the map is wrapped in a new {@code Node}.
     *
     * @return the value as a map of nodes.
     * @throws IllegalStateException if the value is not a map or contains non-string keys.
     */
    public Map<String, Node> asMap() {
        if (this.value instanceof Map<?, ?> map) {
            Map<String, Node> result = new LinkedHashMap<>();
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (!(entry.getKey() instanceof String)) {
                    throw new IllegalStateException("Map contains non-string key: " + entry.getKey());
                }
                result.put((String) entry.getKey(), new Node(entry.getValue()));
            }
            return result;
        }
        throw new IllegalStateException("Value is not a map: " + this.value);
    }

    /**
     * Checks whether this node represents a {@code null} value.
     *
     * @return {@code true} if the underlying value is {@code null}, {@code false} otherwise.
     */
    public boolean isNull() {
        return this.value == null;
    }

    /**
     * Returns the raw underlying value represented by this node.
     *
     * @return the raw value, which may be {@code null}.
     */
    public Object raw() {
        return this.value;
    }
}
