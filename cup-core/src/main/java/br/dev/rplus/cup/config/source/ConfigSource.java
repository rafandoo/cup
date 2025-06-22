package br.dev.rplus.cup.config.source;

import java.util.Map;

/**
 * Functional interface representing a configuration source
 * (e.g. YAML file, {@code .properties} file, database, remote service).
 */
@FunctionalInterface
public interface ConfigSource {

    /**
     * Loads configuration data and returns it as a {@link Map}.
     *
     * @return a map with configuration keys and values
     */
    Map<String, Object> load();
}
