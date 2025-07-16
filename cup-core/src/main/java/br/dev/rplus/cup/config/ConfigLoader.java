package br.dev.rplus.cup.config;

import br.dev.rplus.cup.config.source.ConfigSource;
import br.dev.rplus.cup.config.source.PropertiesConfigSource;
import br.dev.rplus.cup.config.source.YamlConfigSource;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utility class that detects the file type (YAML or {@code .properties}),
 * loads the data through the proper {@link ConfigSource},
 * and returns a ready-to-use {@link Config} instance.
 */
@UtilityClass
public final class ConfigLoader {

    /**
     * Loads a configuration file from a {@link Path}.
     *
     * @param path the path to the configuration file.
     * @return a populated {@link Config} instance.
     * @throws RuntimeException if an I/O error occurs.
     */
    public static Config from(Path path) {
        try (InputStream input = Files.newInputStream(path)) {
            return from(input, path.toString());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao abrir arquivo", e);
        }
    }

    /**
     * Loads a configuration file from a string path.
     *
     * @param path the path to the configuration file.
     * @return a populated {@link Config} instance.
     */
    public static Config from(String path) {
        return from(Path.of(path));
    }

    /**
     * Loads configuration data from an {@link InputStream}, inferring
     * the format from the file name.
     *
     * @param input    the open input stream.
     * @param filename the file name (used only to detect the extension).
     * @return a populated {@link Config} instance.
     * @throws IllegalArgumentException if the file type is unsupported.
     */
    public static Config from(InputStream input, String filename) {
        ConfigSource source;
        if (filename.endsWith(".yml") || filename.endsWith(".yaml")) {
            source = new YamlConfigSource();
        } else if (filename.endsWith(".properties")) {
            source = new PropertiesConfigSource();
        } else {
            throw new IllegalArgumentException("Formato de arquivo não suportado: " + filename);
        }

        return new Config(source.load(input));
    }

    /**
     * Loads configuration data from an {@link InputStream}.
     *
     * @param input  the open input stream.
     * @param source the {@link ConfigSource} to use.
     * @return a populated {@link Config} instance.
     */
    public static Config from(InputStream input, ConfigSource source) {
        return new Config(source.load(input));
    }
}
