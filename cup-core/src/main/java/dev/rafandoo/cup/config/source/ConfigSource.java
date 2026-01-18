package dev.rafandoo.cup.config.source;

import java.io.InputStream;
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
     * @param input the input stream of the configuration source.
     * @return a map with configuration keys and values
     */
    Map<String, Object> load(InputStream input);
}
