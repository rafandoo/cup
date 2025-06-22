package br.dev.rplus.cup.config.source;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class YamlConfigSource implements ConfigSource {

    private final InputStream input;

    public YamlConfigSource(InputStream input) {
        this.input = input;
    }

    @Override
    public Map<String, Object> load() {
        Yaml yaml = new Yaml();
        return yaml.load(input);
    }
}
