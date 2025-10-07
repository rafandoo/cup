package dev.rafandoo.cup.config;

import dev.rafandoo.cup.config.source.ConfigSource;
import dev.rafandoo.cup.config.source.PropertiesConfigSource;
import dev.rafandoo.cup.config.source.YamlConfigSource;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utility class that detects the file type (YAML or {@code .properties}),
 * loads the data through the proper {@link ConfigSource},
 * and returns a ready-to-use {@link Config} instance.
 */
@Slf4j
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
            throw new RuntimeException("Error opening configuration file", e);
        }
    }

    /**
     * Loads a configuration file from a {@link Path} using a specific {@link ConfigSource}.
     *
     * @param path   the path to the configuration file.
     * @param source the {@link ConfigSource} to use for loading the configuration.
     * @return a populated {@link Config} instance.
     * @throws RuntimeException if an I/O error occurs while opening the file.
     */
    public static Config from(Path path, ConfigSource source) {
        try (InputStream input = Files.newInputStream(path)) {
            return from(input, source);
        } catch (IOException e) {
            throw new RuntimeException("Error opening configuration file", e);
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
     * Loads a configuration file from a string path using a specific {@link ConfigSource}.
     *
     * @param path   the path to the configuration file.
     * @param source the {@link ConfigSource} to use for loading the configuration.
     * @return a populated {@link Config} instance.
     */
    public static Config from(String path, ConfigSource source) {
        return from(Path.of(path), source);
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
        log.info("Loading configuration from {}", filename);
        if (filename.endsWith(".yml") || filename.endsWith(".yaml")) {
            source = new YamlConfigSource();
        } else if (filename.endsWith(".properties")) {
            source = new PropertiesConfigSource();
        } else {
            throw new IllegalArgumentException("Unsupported file format: " + filename);
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
