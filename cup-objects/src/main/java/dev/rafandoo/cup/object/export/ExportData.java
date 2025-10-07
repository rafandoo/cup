package dev.rafandoo.cup.object.export;

import dev.rafandoo.cup.object.export.strategies.JsonExportStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * Utility class to export data to a file from an object.
 */
@Slf4j
@Setter
@NoArgsConstructor
public class ExportData {

    private ExportStrategy exportStrategy;

    private String filename;

    @Getter
    private String path;

    /**
     * Constructor with export strategy.
     *
     * @param exportStrategy the export strategy to be used.
     */
    public ExportData(ExportStrategy exportStrategy) {
        this.exportStrategy = exportStrategy;
    }

    /**
     * Constructor with export strategy and filename.
     *
     * @param exportStrategy the export strategy to be used.
     * @param filename       the file where the data will be exported.
     */
    public ExportData(ExportStrategy exportStrategy, String filename) {
        this.exportStrategy = exportStrategy;
        this.filename = filename;
    }

    /**
     * Constructor with export strategy, filename and path.
     *
     * @param exportStrategy the export strategy to be used.
     * @param filename       the file where the data will be exported.
     * @param path           the path where the file will be saved.
     */
    public ExportData(ExportStrategy exportStrategy, String filename, String path) {
        this.exportStrategy = exportStrategy;
        this.filename = filename;
        this.path = path;
    }

    public ExportStrategy getExportStrategy() {
        try {
            if (this.exportStrategy == null) {
                this.exportStrategy = new JsonExportStrategy();
            }
            return exportStrategy;
        } catch (Exception e) {
            log.error("Error loading export strategy. Error: {}", e.getMessage());
            return null;
        }
    }

    public void setExportStrategy(ExportStrategy exportStrategy) {
        this.exportStrategy = exportStrategy;
    }

    public void setExportStrategy(ExportType exportType) {
        try {
            this.exportStrategy = exportType.getStrategyClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("Error setting export strategy. Error: {}", e.getMessage());
        }
    }

    public String getFilename() {
        if (this.filename == null) {
            this.filename = String.format("export.%s", this.getExportStrategy().getExtension());
        }
        if (this.filename.contains(".")) {
            return this.filename;
        }
        return String.format("%s.%s", this.filename, this.getExportStrategy().getExtension());
    }

    /**
     * Exports the given object using one of the available strategies to a given file.
     *
     * @param object the object to be exported.
     */
    public void export(Object object) {
        File file;
        try {
            String path = this.getPath();
            if (path == null) {
                file = new File(getFilename());
            } else {
                if (!path.endsWith("/") && !path.endsWith("\\")) {
                    path += File.separator;
                }
                path += getFilename();
                file = new File(path);
            }
            this.getExportStrategy().export(object, file);
        } catch (Exception e) {
            log.error("Error exporting object. Error: {}", e.getMessage());
        }
    }

    /**
     * Exports the given object using one of the available strategies to a String.
     *
     * @param object the object to be exported.
     * @return the exported object as a String.
     */
    public String exportToString(Object object) {
        try {
            return this.getExportStrategy().export(object);
        } catch (Exception e) {
            log.error("Error exporting object to string. Error: {}", e.getMessage());
            return null;
        }
    }
}
