package br.dev.rplus.cup.config.source;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * {@link ConfigSource} implementation for YAML files.
 */
public class YamlConfigSource implements ConfigSource {

    private final InputStream input;

    /**
     * Creates a new YAML config source.
     *
     * @param input the input stream of the YAML file
     */
    public YamlConfigSource(InputStream input) {
        this.input = input;
    }

    @Override
    public Map<String, Object> load() {
        Yaml yaml = new Yaml();
        return yaml.load(input);
    }
}
