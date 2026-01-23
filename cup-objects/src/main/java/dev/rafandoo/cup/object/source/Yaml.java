package dev.rafandoo.cup.object.source;

import java.io.InputStream;
import java.util.Map;

/**
 * {@link ObjectSource} implementation for YAML documents.
 * <p>
 * This source parses YAML data into a hierarchical {@link Map}
 * structure, preserving nested sections and values.
 */
public class Yaml implements ObjectSource {

    @Override
    public Map<String, Object> load(InputStream input) {
        org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();
        return yaml.load(input);
    }
}
