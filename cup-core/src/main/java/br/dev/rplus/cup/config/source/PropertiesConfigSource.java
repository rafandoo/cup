package br.dev.rplus.cup.config.source;

import java.io.*;
import java.util.*;

public class PropertiesConfigSource implements ConfigSource {

    private final InputStream input;

    public PropertiesConfigSource(InputStream input) {
        this.input = input;
    }

    @Override
    public Map<String, Object> load() {
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
