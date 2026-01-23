package dev.rafandoo.cup.object.loader;

import dev.rafandoo.cup.object.source.*;
import dev.rafandoo.cup.object.tree.ObjectTree;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utility class responsible for loading structured documents
 * (such as YAML, TOML, JSON, or {@code .properties}) and converting them
 * into an {@link ObjectTree}.
 */
@Slf4j
@UtilityClass
public final class ObjectLoader {

    /**
     * Loads a structured document from a {@link Path}, automatically
     * inferring the document format from the file extension.
     *
     * @param path the path to the document file.
     * @return a populated {@link ObjectTree} representing the parsed document.
     * @throws RuntimeException         if an I/O error occurs while opening the file.
     * @throws IllegalArgumentException if the file format is unsupported.
     */
    public static ObjectTree from(Path path) {
        try (InputStream input = Files.newInputStream(path)) {
            return from(input, path.toString());
        } catch (IOException e) {
            throw new RuntimeException("Error opening document file", e);
        }
    }

    /**
     * Loads a structured document from a {@link Path} using a specific
     * {@link ObjectSource} implementation.
     *
     * @param path   the path to the document file.
     * @param source the {@link ObjectSource} class responsible for parsing the input.
     * @return a populated {@link ObjectTree} representing the parsed document.
     * @throws RuntimeException if an I/O error occurs or the source cannot be instantiated.
     */
    public static ObjectTree from(Path path, Class<? extends ObjectSource> source) {
        try (InputStream input = Files.newInputStream(path)) {
            return from(input, source);
        } catch (IOException e) {
            throw new RuntimeException("Error opening document file", e);
        }
    }

    /**
     * Loads a structured document from a string path, automatically
     * inferring the document format from the file extension.
     *
     * @param path the document file path.
     * @return a populated {@link ObjectTree} representing the parsed document.
     * @throws IllegalArgumentException if the file format is unsupported.
     */
    public static ObjectTree from(String path) {
        return from(Path.of(path));
    }

    /**
     * Loads a structured document from a string path using a specific
     * {@link ObjectSource} implementation.
     *
     * @param path   the document file path.
     * @param source the {@link ObjectSource} class responsible for parsing the input.
     * @return a populated {@link ObjectTree} representing the parsed document.
     * @throws RuntimeException if the source cannot be instantiated.
     */
    public static ObjectTree from(String path, Class<? extends ObjectSource> source) {
        return from(Path.of(path), source);
    }

    /**
     * Loads structured document data from an {@link InputStream},
     * inferring the document format from the provided file name.
     * <p>
     * Supported formats include:
     * <ul>
     *   <li>YAML ({@code .yml}, {@code .yaml})</li>
     *   <li>TOML ({@code .toml})</li>
     *   <li>JSON ({@code .json})</li>
     *   <li>Java Properties ({@code .properties})</li>
     * </ul>
     *
     * @param input    an open input stream containing the document data.
     * @param filename the file name used solely for format detection.
     * @return a populated {@link ObjectTree} representing the parsed document.
     * @throws IllegalArgumentException if the file format is unsupported.
     */
    public static ObjectTree from(InputStream input, String filename) {
        ObjectSource source;
        log.info("Loading document from {}", filename);
        if (filename.endsWith(".yml") || filename.endsWith(".yaml")) {
            source = new Yaml();
        } else if (filename.endsWith(".toml")) {
            source = new Toml();
        } else if (filename.endsWith(".properties")) {
            source = new Properties();
        } else if (filename.endsWith(".json")) {
            source = new Json();
        } else {
            throw new IllegalArgumentException("Unsupported document format: " + filename);
        }

        return new ObjectTree(source.load(input));
    }

    /**
     * Loads structured document data from an {@link InputStream}
     * using an explicitly provided {@link ObjectSource} implementation.
     *
     * @param input  an open input stream containing the document data.
     * @param source the {@link ObjectSource} class responsible for parsing the input.
     * @return a populated {@link ObjectTree} representing the parsed document.
     * @throws RuntimeException if the source cannot be instantiated.
     */
    public static ObjectTree from(InputStream input, Class<? extends ObjectSource> source) {
        ObjectSource objectSource;
        try {
            objectSource = source.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error instantiating ObjectSource: " + source.getName(), e);
        }

        return new ObjectTree(objectSource.load(input));
    }
}
