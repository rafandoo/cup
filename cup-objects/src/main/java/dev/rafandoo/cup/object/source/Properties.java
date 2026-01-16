package dev.rafandoo.cup.object.source;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@link ObjectSource} implementation for classic {@code .properties} documents.
 * <p>
 * Properties are loaded as a flat key-value structure, where both keys
 * and values are represented as {@link String}s.
 */
@Slf4j
public class Properties implements ObjectSource {

    @Override
    public Map<String, Object> load(InputStream input) {
        java.util.Properties properties = new java.util.Properties();
        try {
            properties.load(input);
        } catch (IOException e) {
            log.error("Error loading .properties.", e);
            return new HashMap<>();
        }

        Map<String, Object> map = new LinkedHashMap<>();
        properties.stringPropertyNames().forEach(
            name -> map.put(name, properties.getProperty(name))
        );
        return map;
    }
}
