package br.dev.rplus.cup.object.export;

import br.dev.rplus.cup.log.Logger;

import java.io.File;

/**
 * Utility class to export data to a file from an object.
 */
public class ExportData {

    /**
     * Instance of the export strategy.
     */
    private ExportStrategy exportStrategy;

    /**
     * File where the data will be exported.
     */
    private File file;

    private static final String DEFAULT_FILE_NAME = "export.txt";

    /**
     * Default constructor.
     */
    public ExportData() {
    }

    /**
     * Constructor with export strategy.
     *
     * @param exportStrategy the export strategy to be used.
     */
    public ExportData(ExportStrategy exportStrategy) {
        this.exportStrategy = exportStrategy;
    }

    /**
     * Constructor with export strategy and file.
     *
     * @param exportStrategy the export strategy to be used.
     * @param file           the file where the data will be exported.
     */
    public ExportData(ExportStrategy exportStrategy, File file) {
        this.exportStrategy = exportStrategy;
        this.file = file;
    }

    /**
     * Gets the export strategy.
     *
     * @return the export strategy.
     */
    public ExportStrategy getExportStrategy() {
        try {
            if (this.exportStrategy == null) {
                this.exportStrategy = new JsonExportStrategy();
            }
            return exportStrategy;
        } catch (Exception e) {
            Logger.error("Error loading export strategy.", e);
            return null;
        }
    }

    /**
     * Sets the export strategy.
     *
     * @param exportStrategy the export strategy to be set.
     */
    public void setExportStrategy(ExportStrategy exportStrategy) {
        this.exportStrategy = exportStrategy;
    }

    /**
     * Gets the file where the data will be exported.
     *
     * @return the file where the data will be exported.
     */
    public File getFile() {
        if (this.file == null) {
            this.file = new File(DEFAULT_FILE_NAME);
        }
        return file;
    }

    /**
     * Exports the given object using the default export strategy and file.
     *
     * @param object the object to be exported.
     */
    public void export(Object object) {
        try {
            this.getExportStrategy().export(object, getFile());
        } catch (Exception e) {
            Logger.error("Error exporting object.", e);
        }
    }

    /**
     * Exports the given object using the specified export type.
     * <p>
     * This method sets the export strategy based on the provided `ExportType` and uses the default file.
     *
     * @param object     the object to be exported.
     * @param exportType the type of export to be used, determining the export strategy.
     */
    public void export(Object object, ExportType exportType) {
        try {
            this.setExportStrategy(exportType.getStrategyClass().getDeclaredConstructor().newInstance());
            this.getExportStrategy().export(object, getFile());
        } catch (Exception e) {
            Logger.error("Error exporting object.", e);
        }
    }

    /**
     * Exports the given object using the specified export type and file.
     * <p>
     * This method sets the export strategy based on the provided `ExportType` and uses the specified file.
     *
     * @param object     the object to be exported.
     * @param exportType the type of export to be used, determining the export strategy.
     * @param file       the file to which the object should be exported.
     */
    public void export(Object object, ExportType exportType, File file) {
        try {
            this.setExportStrategy(exportType.getStrategyClass().getDeclaredConstructor().newInstance());
            this.getExportStrategy().export(object, file);
        } catch (Exception e) {
            Logger.error("Error exporting object.", e);
        }
    }
}
