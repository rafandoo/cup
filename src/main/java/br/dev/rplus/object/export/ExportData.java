package br.dev.rplus.object.export;

import br.dev.rplus.log.LoggerFactory;

import java.io.File;

public class ExportData {

    private ExportStrategy exportStrategy;
    private File file;

    public ExportData() {

    }

    public ExportData(ExportStrategy exportStrategy) {
        this.exportStrategy = exportStrategy;
    }

    public ExportData(ExportStrategy exportStrategy, File file) {
        this.exportStrategy = exportStrategy;
        this.file = file;
    }

    public ExportStrategy getExportStrategy() {
        try {
            if (this.exportStrategy == null) {
                this.exportStrategy = new JsonExportStrategy();
            }
            return exportStrategy;
        } catch (Exception e) {
            LoggerFactory.error("Error loading export strategy: <br>%s", e.getMessage());
            return null;
        }
    }

    public void setExportStrategy(ExportStrategy exportStrategy) {
        this.exportStrategy = exportStrategy;
    }

    public File getFile() {
        if (this.file == null) {
            this.file = new File("export.txt");
        }
        return file;
    }

    public void export(Object object) {
        try {
            getExportStrategy().export(object, getFile());
        } catch (Exception e) {
            LoggerFactory.error("Error exporting object: <br>%s", e.getMessage());
        }
    }

    public void export(Object object, ExportType exportType) {
        try {
            setExportStrategy(exportType.getStrategyClass().newInstance());
            getExportStrategy().export(object, getFile());
        } catch (Exception e) {
            LoggerFactory.error("Error exporting object: <br>%s", e.getMessage());
        }
    }

    public void export(Object object, ExportType exportType, File file) {
        try {
            setExportStrategy(exportType.getStrategyClass().newInstance());
            getExportStrategy().export(object, file);
        } catch (Exception e) {
            LoggerFactory.error("Error exporting object: <br>%s", e.getMessage());
        }
    }
}
