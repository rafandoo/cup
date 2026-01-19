package dev.rafandoo.cup.object.export.strategy;

import dev.rafandoo.cup.object.export.ExportData;

import java.io.File;

/**
 * Strategy interface for exporting objects to different formats.
 *
 * @see ExportData
 */
public interface ExportStrategy {

    /**
     * Get the extension of the file.
     *
     * @return file extension without dot (e.g. "json", "csv").
     */
    String getExtension();

    /**
     * Returns the MIME type of the exported content.
     *
     * @return the MIME type as a string.
     */
    String getMimeType();

    /**
     * Exports the given object to a string representation.
     *
     * @param object the object to export.
     * @return exported content as string.
     */
    String export(Object object);

    /**
     * Exports the given object to a file.
     *
     * @param object the object to export.
     * @param file   destination file.
     */
    void export(Object object, File file);
}
