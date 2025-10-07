package dev.rafandoo.cup.object.export;

import java.io.File;

/**
 * Interface for Export strategy.
 *
 * @see ExportData
 */
public interface ExportStrategy {

    /**
     * Get the extension of the file.
     *
     * @return the extension of the file.
     */
    String getExtension();

    /**
     * Get the content type of the file.
     *
     * @return the content type of the file.
     */
    String getContentType();

    /**
     * Export object to string.
     *
     * @param object Object to be exported.
     * @return String representation of the object.
     */
    String export(Object object);

    /**
     * Export object to file.
     *
     * @param object Object to be exported.
     * @param file   File to be exported.
     */
    void export(Object object, File file);
}
