package dev.rafandoo.cup.object.export.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * An extension of the {@link ExportStrategy} interface that provides a method to retrieve the {@link ObjectMapper} instance used for exporting objects.
 *
 * @see ExportStrategy
 */
public interface JacksonExportStrategy extends ExportStrategy {

    /**
     * Gets the {@link ObjectMapper} instance used for exporting objects.
     *
     * @return the {@link ObjectMapper} instance.
     */
    ObjectMapper getMapper();
}
