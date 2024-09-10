package br.dev.rplus.cup.object.export;

import java.io.File;

/**
 * Interface for Export strategy.
 *
 * @see ExportData
 */
public interface ExportStrategy {

    /**
     * Export object to file.
     *
     * @param object Object to be exported.
     * @param file   File to be exported.
     */
    void export(Object object, File file);
}
