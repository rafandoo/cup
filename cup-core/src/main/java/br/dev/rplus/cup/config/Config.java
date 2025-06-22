package br.dev.rplus.cup.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a hierarchical configuration loaded from an external source
 * (YAML, {@code .properties}, etc.).
 * Values can be accessed using dot notation keys
 * (e.g. {@code "database.host"} or {@code "server.ssl.enabled"}).
 */
@SuppressWarnings("unused")
public class Config {
    private final Map<String, Object> data;

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
        Object current = data;
        for (String k : keys) {
            if (!(current instanceof Map)) {
                return new Value(null);
            }
            current = ((Map<?, ?>) current).get(k);
        }
        return new Value(current);
    }
}
