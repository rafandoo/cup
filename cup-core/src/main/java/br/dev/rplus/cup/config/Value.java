package br.dev.rplus.cup.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class Value {
    private final Object value;

    public Value(Object value) {
        this.value = value;
    }

    public String asString() {
        return value != null ? value.toString() : null;
    }

    public int asInt() {
        if (value instanceof Number) return ((Number) value).intValue();
        if (value instanceof String) return Integer.parseInt((String) value);
        throw new IllegalStateException("Cannot convert to int: " + value);
    }

    public boolean asBoolean() {
        if (value instanceof Boolean) return (Boolean) value;
        if (value instanceof String) return Boolean.parseBoolean((String) value);
        throw new IllegalStateException("Cannot convert to boolean: " + value);
    }

    public double asDouble() {
        if (value instanceof Number) return ((Number) value).doubleValue();
        if (value instanceof String) return Double.parseDouble((String) value);
        throw new IllegalStateException("Cannot convert to double: " + value);
    }

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

    public boolean isNull() {
        return value == null;
    }

    public Object raw() {
        return value;
    }
}
