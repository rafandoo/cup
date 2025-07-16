package br.dev.rplus.cup.config.source;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * {@link ConfigSource} implementation for classic {@code .properties} files.
 */
public class PropertiesConfigSource implements ConfigSource {

    @Override
    public Map<String, Object> load(InputStream input) {
        Properties properties = new Properties();
        try {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar .properties", e);
        }

        Map<String, Object> map = new HashMap<>();
        for (String name : properties.stringPropertyNames()) {
            map.put(name, properties.getProperty(name));
        }
        return map;
    }
}
