package br.dev.rplus.cup.object.export;

/**
 * Enum for export types (export strategies).
 *
 * @see ExportStrategy
 */
public enum ExportType {

    JSON(JsonExportStrategy.class),
    XML(XmlExportStrategy.class),
    CSV(CsvExportStrategy.class);

    private final Class<? extends ExportStrategy> strategyClass;

    ExportType(Class<? extends ExportStrategy> strategyClass) {
        this.strategyClass = strategyClass;
    }

    public Class<? extends ExportStrategy> getStrategyClass() {
        return strategyClass;
    }
}
