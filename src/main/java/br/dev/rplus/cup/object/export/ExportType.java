package br.dev.rplus.cup.object.export;

/**
 * Enum for export types (export strategies).
 *
 * @see ExportStrategy
 */
public enum ExportType {

    /**
     * JSON export strategy.
     */
    JSON(JsonExportStrategy.class),

    /**
     * XML export strategy.
     */
    XML(XmlExportStrategy.class),

    /**
     * CSV export strategy.
     */
    CSV(CsvExportStrategy.class);

    private final Class<? extends ExportStrategy> strategyClass;

    ExportType(Class<? extends ExportStrategy> strategyClass) {
        this.strategyClass = strategyClass;
    }

    /**
     * Gets the class of the export strategy.
     *
     * @return the class of the export strategy.
     */
    public Class<? extends ExportStrategy> getStrategyClass() {
        return strategyClass;
    }
}
