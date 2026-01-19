package dev.rafandoo.cup.object.tree;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a hierarchical, structured document loaded from an external source
 * (such as YAML, TOML, JSON, or {@code .properties}).
 */
public class ObjectTree {

    private final Map<String, Object> data;

    private static final ObjectMapper MAPPER = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

    /**
     * Creates a new {@code ObjectTree} backed by the provided hierarchical data.
     *
     * @param data a map representing the root of the parsed document.
     */
    public ObjectTree(Map<String, Object> data) {
        this.data = new LinkedHashMap<>();
        this.data.putAll(data);
    }

    /**
     * Retrieves a node from the document using a dot-notation path.
     * <p>
     * If any path segment does not exist or does not reference a map,
     * an empty {@link Node} wrapping {@code null} is returned.
     *
     * @param key the dot-notation path (e.g. {@code "server.port"}).
     * @return a {@link Node} representing the resolved value or {@code null}.
     */
    public Node get(String key) {
        String[] keys = key.split("\\.");
        Object current = this.data;
        for (String k : keys) {
            if (!(current instanceof Map)) {
                return new Node(null);
            }
            current = ((Map<?, ?>) current).get(k);
        }
        return new Node(current);
    }

    /**
     * Configures a single {@link DeserializationFeature} on the internal
     * {@link ObjectMapper} used for object conversion.
     * <p>
     * This affects all subsequent {@code as(...)} conversion operations.
     *
     * @param feature the deserialization feature to configure.
     * @param state   the boolean state to set for the feature.
     */
    public void configureMapper(DeserializationFeature feature, boolean state) {
        MAPPER.configure(feature, state);
    }

    /**
     * Configures multiple {@link DeserializationFeature}s on the internal
     * {@link ObjectMapper} used for object conversion.
     * <p>
     * This affects all subsequent {@code as(...)} conversion operations.
     *
     * @param features a map of deserialization features and their desired states.
     */
    public void configureMapper(Map<DeserializationFeature, Boolean> features) {
        features.forEach(MAPPER::configure);
    }

    /**
     * Converts the entire document tree into an instance of the specified type.
     *
     * @param clazz the target class to convert the document into.
     * @return an instance of the specified class.
     * @throws IllegalArgumentException if the conversion fails.
     */
    public <T> T as(Class<T> clazz) {
        return MAPPER.convertValue(this.data, clazz);
    }

    /**
     * Converts a specific subtree of the document into an instance of
     * the specified type.
     *
     * @param clazz the target class to convert the value into.
     * @param key   the dot-notation path to the desired subtree.
     * @return an instance of the specified class.
     * @throws IllegalArgumentException if the conversion fails.
     */
    public <T> T as(Class<T> clazz, String key) {
        return MAPPER.convertValue(this.get(key), clazz);
    }

    /**
     * Converts the entire document tree into an instance of the specified type,
     * returning {@code null} if the conversion fails.
     *
     * @param clazz the target class to convert the document into.
     * @return an instance of the specified class, or {@code null} on failure.
     */
    public <T> T asOrNull(Class<T> clazz) {
        try {
            return this.as(clazz);
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Converts a specific subtree of the document into an instance of
     * the specified type, returning {@code null} if the conversion fails.
     *
     * @param clazz the target class to convert the value into.
     * @param key   the dot-notation path to the desired subtree.
     * @return an instance of the specified class, or {@code null} on failure.
     */
    public <T> T asOrNull(Class<T> clazz, String key) {
        try {
            return this.as(clazz, key);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Converts the entire document tree into an {@link Optional} of the specified type.
     *
     * @param clazz the target class to convert the document into.
     * @return an {@link Optional} containing the converted value, or empty on failure.
     */
    public <T> Optional<T> tryAs(Class<T> clazz) {
        try {
            return Optional.ofNullable(this.as(clazz));
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }

    /**
     * Converts a specific subtree of the document into an {@link Optional}
     * of the specified type.
     *
     * @param clazz the target class to convert the value into.
     * @param key   the dot-notation path to the desired subtree.
     * @return an {@link Optional} containing the converted value, or empty on failure.
     */
    public <T> Optional<T> tryAs(Class<T> clazz, String key) {
        try {
            return Optional.ofNullable(this.as(clazz, key));
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }
}
