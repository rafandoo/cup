package dev.rafandoo.cup.object.export;

import dev.rafandoo.cup.object.export.strategy.Csv;
import dev.rafandoo.cup.object.export.strategy.ExportStrategy;
import dev.rafandoo.cup.object.export.strategy.Json;
import dev.rafandoo.cup.object.export.strategy.Xml;

/**
 * Defines the supported export formats and acts as a factory for
 * their corresponding {@link ExportStrategy} implementations.
 *
 * @see ExportStrategy
 */
public enum ExportType {

    JSON {
        @Override
        public ExportStrategy createStrategy() {
            return new Json();
        }
    },

    XML {
        @Override
        public ExportStrategy createStrategy() {
            return new Xml();
        }
    },

    CSV {
        @Override
        public ExportStrategy createStrategy() {
            return new Csv();
        }
    };

    /**
     * Creates a new instance of the corresponding {@link ExportStrategy}.
     *
     * @return a new export strategy instance.
     */
    public abstract ExportStrategy createStrategy();
}
