package br.dev.rplus.cup.config;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class Config {
    private final Map<String, Object> data;

    public Config(Map<String, Object> data) {
        this.data = new HashMap<>();
        this.data.putAll(data);
    }

    public Value get(String key) {
        String[] keys = key.split("\\.");
        Object current = data;
        for (String k : keys) {
            if (!(current instanceof Map)) {
                return new Value(null);
            }
            current = ((Map<?, ?>) current).get(key);
        }
        return new Value(current);
    }
}
