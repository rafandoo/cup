package br.dev.rplus.cup.object.export;

import br.dev.rplus.cup.object.export.strategies.CsvExportStrategy;
import br.dev.rplus.cup.object.export.strategies.JsonExportStrategy;
import br.dev.rplus.cup.object.export.strategies.XmlExportStrategy;

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

    /**
     * Default constructor.
     *
     * @param strategyClass the class of the export strategy.
     */
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
