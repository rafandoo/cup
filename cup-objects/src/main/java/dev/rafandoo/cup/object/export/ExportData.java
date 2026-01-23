package dev.rafandoo.cup.object.export;

import dev.rafandoo.cup.exception.ExportException;
import dev.rafandoo.cup.object.export.strategy.ExportStrategy;
import dev.rafandoo.cup.text.StringValidator;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Handles exporting objects using a configurable {@link ExportStrategy}.
 */
@Slf4j
public class ExportData {

    private final ExportStrategy strategy;
    private final String filename;
    private final Path directory;

    /**
     * Internal constructor used by factory methods.
     *
     * @param strategy  the export strategy to use (never {@code null}).
     * @param filename  the output filename (may be {@code null} or blank).
     * @param directory the output directory (may be {@code null}).
     */
    private ExportData(ExportStrategy strategy, String filename, Path directory) {
        this.strategy = Objects.requireNonNull(strategy, "ExportStrategy must not be null");
        this.filename = filename;
        this.directory = directory;
    }

    /**
     * Instantiates the given {@link ExportStrategy} class.
     *
     * @param strategy the export strategy class to instantiate.
     * @return a new instance of the specified export strategy.
     * @throws ExportException if instantiation fails.
     */
    private static ExportStrategy instantiateStrategy(Class<? extends ExportStrategy> strategy) {
        try {
            return strategy.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new ExportException("Failed to instantiate export strategy", e);
        }
    }

    /**
     * Creates an exporter for the given {@link ExportStrategy}.
     *
     * @param strategy the export strategy to use.
     * @return a configured {@code ExportData} instance.
     */
    public static ExportData of(Class<? extends ExportStrategy> strategy) {
        return new ExportData(instantiateStrategy(strategy), null, null);
    }

    /**
     * Creates an exporter for the given {@link ExportStrategy} and filename.
     *
     * @param strategy the export strategy to use.
     * @param filename the output filename (extension optional).
     * @return a configured {@code ExportData} instance.
     */
    public static ExportData of(Class<? extends ExportStrategy> strategy, String filename) {
        return new ExportData(instantiateStrategy(strategy), filename, null);
    }

    /**
     * Creates an exporter for the given {@link ExportStrategy}, filename and directory.
     *
     * @param strategy  the export strategy to use.
     * @param filename  the output filename (extension optional).
     * @param directory the output directory path.
     * @return a fully configured {@code ExportData} instance.
     */
    public static ExportData of(Class<? extends ExportStrategy> strategy, String filename, String directory) {
        Path dir = directory == null ? null : Paths.get(directory);
        return new ExportData(instantiateStrategy(strategy), filename, dir);
    }

    /**
     * Exports the given object to a file using the configured strategy.
     *
     * @param object the object to export.
     * @return the generated file.
     * @throws ExportException if the export fails.
     */
    public File export(Object object) {
        try {
            File file = this.resolveFile();
            this.strategy.export(object, file);
            return file;
        } catch (Exception e) {
            throw new ExportException("Failed to export object to file", e);
        }
    }

    /**
     * Exports the given object to its string representation.
     *
     * @param object the object to export.
     * @return the exported content as string.
     * @throws ExportException if the export fails.
     */
    public String exportToString(Object object) {
        try {
            return this.strategy.export(object);
        } catch (Exception e) {
            throw new ExportException("Failed to export object to string", e);
        }
    }

    /**
     * Resolves the output file based on directory and filename configuration.
     *
     * @return the resolved output file.
     */
    private File resolveFile() {
        String resolvedName = this.resolveFilename();

        if (this.directory == null) {
            return new File(resolvedName);
        }

        return this.directory.resolve(resolvedName).toFile();
    }

    /**
     * Resolves the output filename using the configured strategy.
     *
     * @return the resolved filename.
     */
    private String resolveFilename() {
        String extension = this.strategy.getExtension();

        if (StringValidator.isNullOrBlank(filename)) {
            return "export." + extension;
        }

        if (this.filename.contains(".")) {
            return this.filename;
        }

        return this.filename + "." + extension;
    }
}
