package dev.rafandoo.cup.config.source;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * {@link ConfigSource} implementation for YAML files.
 */
public class YamlConfigSource implements ConfigSource {

    @Override
    public Map<String, Object> load(InputStream input) {
        Yaml yaml = new Yaml();
        return yaml.load(input);
    }
}
