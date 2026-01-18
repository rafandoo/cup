package dev.rafandoo.cup.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a hierarchical configuration loaded from an external source
 * (YAML, {@code .properties}, etc.).
 * Values can be accessed using dot notation keys
 * (e.g. {@code "database.host"} or {@code "server.ssl.enabled"}).
 */
public class Config {

    private final Map<String, Object> data;

    private static final ObjectMapper MAPPER = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

    /**
     * Creates a new {@code Config} instance.
     *
     * @param data a map containing the configuration entries
     */
    public Config(Map<String, Object> data) {
        this.data = new HashMap<>();
        this.data.putAll(data);
    }

    /**
     * Retrieves a value using a dot-notation key.
     * If any segment does not reference a map or the key is missing,
     * {@code Value(null)} is returned.
     *
     * @param key the key in dot notation.
     * @return a {@link Value} wrapper for the resulting object.
     */
    public Value get(String key) {
        String[] keys = key.split("\\.");
        Object current = this.data;
        for (String k : keys) {
            if (!(current instanceof Map)) {
                return new Value(null);
            }
            current = ((Map<?, ?>) current).get(k);
        }
        return new Value(current);
    }

    /**
     * Configures a specific {@link DeserializationFeature} on the internal ObjectMapper.
     *
     * @param feature the deserialization feature to configure.
     * @param state   the boolean state to set for the feature.
     */
    public void configureMapper(DeserializationFeature feature, boolean state) {
        MAPPER.configure(feature, state);
    }

    /**
     * Configures multiple {@link DeserializationFeature}s on the internal ObjectMapper.
     *
     * @param features a map of deserialization features and their corresponding boolean states to set.
     */
    public void configureMapper(Map<DeserializationFeature, Boolean> features) {
        features.forEach(MAPPER::configure);
    }

    /**
     * Converts the entire configuration data to an object of the specified type.
     *
     * @param clazz the target class to convert the configuration data into.
     * @return an instance of the specified class representing the configuration data.
     */
    public <T> T as(Class<T> clazz) {
        return MAPPER.convertValue(this.data, clazz);
    }

    /**
     * Converts a specific configuration value to an object of the specified type.
     *
     * @param clazz the target class to convert the configuration value into.
     * @param key   the dot-notation key to retrieve the specific configuration value.
     * @return an instance of the specified class representing the configuration value.
     */
    public <T> T as(Class<T> clazz, String key) {
        return MAPPER.convertValue(this.get(key), clazz);
    }

    /**
     * Converts the entire configuration data to an object of the specified type, returning null if conversion fails.
     *
     * @param clazz the target class to convert the configuration data into.
     * @return an instance of the specified class representing the configuration data, or null if conversion fails.
     */
    public <T> T asOrNull(Class<T> clazz) {
        try {
            return this.as(clazz);
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Converts a specific configuration value to an object of the specified type, returning null if conversion fails.
     *
     * @param clazz the target class to convert the configuration value into.
     * @param key   the dot-notation key to retrieve the specific configuration value.
     * @return an instance of the specified class representing the configuration value, or null if conversion fails.
     */
    public <T> T asOrNull(Class<T> clazz, String key) {
        try {
            return this.as(clazz, key);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Converts the entire configuration data to an Optional of the specified type, returning an empty Optional if conversion fails.
     *
     * @param clazz the target class to convert the configuration data into.
     * @return an Optional containing an instance of the specified class representing the configuration data, or an empty Optional if conversion fails.
     */
    public <T> Optional<T> tryAs(Class<T> clazz) {
        try {
            return Optional.ofNullable(this.as(clazz));
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }

    /**
     * Converts a specific configuration value to an Optional of the specified type, returning an empty Optional if conversion fails.
     *
     * @param clazz the target class to convert the configuration value into.
     * @param key   the dot-notation key to retrieve the specific configuration value.
     * @return an Optional containing an instance of the specified class representing the configuration value, or an empty Optional if conversion fails.
     */
    public <T> Optional<T> tryAs(Class<T> clazz, String key) {
        try {
            return Optional.ofNullable(this.as(clazz, key));
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }
}
