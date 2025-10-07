package dev.rafandoo.cup.config.environment;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The {@code Properties} class provides utility methods to load and access
 * configuration properties from a file, either from the file system or the
 * application's resources. This class also provides convenient methods to
 * retrieve system properties.
 */
@Slf4j
@UtilityClass
public final class Properties {

    private static java.util.Properties properties;

    /**
     * Loads properties from a file at the specified path. If {@code resource} is true,
     * it tries to load the file from the application's classpath resources; otherwise,
     * it loads the file from the file system.
     *
     * @param path     the path to the properties file.
     * @param resource if true, loads the file from the classpath resources.
     * @throws IOException if the file is not found or cannot be loaded.
     */
    public static synchronized void load(String path, boolean resource) throws IOException {
        if (properties == null) {
            properties = new java.util.Properties();
        }

        Path filePath;
        if (resource) {
            String root = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
            filePath = Paths.get(root, path);
        } else {
            filePath = Paths.get(path);
        }

        log.debug("Loading properties from: {}", filePath.toAbsolutePath());

        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("Properties file not found: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(filePath.toFile())) {
            properties.load(fis);
        }
    }

    /**
     * Loads properties from a file at the specified path in the file system.
     *
     * @param path the path to the properties file.
     * @throws IOException if the file is not found or cannot be loaded.
     */
    public static void load(String path) throws IOException {
        load(path, false);
    }

    /**
     * Retrieves the value of the specified property.
     *
     * @param key the key of the property to retrieve.
     * @return the value of the property, or {@code null} if not found.
     */
    public static String get(String key) {
        return get(key, null);
    }

    /**
     * Retrieves the value of the specified property, or returns the specified
     * default value if the property is not found.
     *
     * @param key          the key of the property to retrieve.
     * @param defaultValue the default value to return if the property is not found.
     * @return the value of the property, or the default value if not found.
     */
    public static String get(String key, String defaultValue) {
        if (properties == null || properties.isEmpty() || !properties.containsKey(key)) {
            return defaultValue;
        }
        return properties.getProperty(key);
    }

    /**
     * Retrieves all loaded properties.
     *
     * @return a Java Properties object containing the loaded properties.
     */
    public static java.util.Properties getAll() {
        return properties;
    }

    /**
     * Checks if the specified property exists in the loaded properties.
     *
     * @param key the key of the property to check.
     * @return {@code true} if the property exists, {@code false} otherwise.
     */
    public static boolean contains(String key) {
        return properties.containsKey(key);
    }
}
