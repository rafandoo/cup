package dev.rafandoo.cup.object.export;

import dev.rafandoo.cup.object.export.strategies.CsvExportStrategy;
import dev.rafandoo.cup.object.export.strategies.JsonExportStrategy;
import dev.rafandoo.cup.object.export.strategies.XmlExportStrategy;
import lombok.Getter;

/**
 * Enum for export types (export strategies).
 *
 * @see ExportStrategy
 */
@Getter
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

}
