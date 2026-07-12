package dev.rafandoo.cup.object.tree;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rafandoo.cup.JacksonMapperFactory;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a hierarchical, structured document loaded from an external source
 * (such as YAML, TOML, JSON, or {@code .properties}).
 */
public class ObjectTree {

    @Getter
    private final Map<String, Object> data = new LinkedHashMap<>();

    @Setter
    private ObjectMapper mapper;

    /**
     * Creates a new {@code ObjectTree} backed by the provided hierarchical data.
     *
     * @param data a map representing the root of the parsed document.
     */
    public ObjectTree(Map<String, Object> data) {
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
     * Retrieves the underlying {@link ObjectMapper} used for type conversions.
     * <p>
     * If no mapper has been explicitly set, a default mapper is created and returned.
     *
     * @return the {@link ObjectMapper} instance for this tree.
     */
    private ObjectMapper getMapper() {
        if (this.mapper == null) {
            this.mapper = JacksonMapperFactory.defaultMapper();
        }
        return this.mapper;
    }

    /**
     * Converts the entire document tree into an instance of the specified type.
     *
     * @param clazz the target class to convert the document into.
     * @return an instance of the specified class.
     * @throws IllegalArgumentException if the conversion fails.
     */
    public <T> T as(Class<T> clazz) {
        return this.getMapper().convertValue(this.data, clazz);
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
        return this.getMapper().convertValue(this.get(key), clazz);
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
