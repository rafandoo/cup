package dev.rafandoo.cup.object.export.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rafandoo.cup.JacksonMapperFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * {@link ExportStrategy} implementation for exporting objects in JSON format.
 *
 * @see ExportStrategy
 */
@Slf4j
public class Json implements JacksonExportStrategy {

    private final ObjectMapper mapper;

    public Json() {
        this(JacksonMapperFactory.json());
    }

    public Json(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ObjectMapper getMapper() {
        return this.mapper;
    }

    @Override
    public String getExtension() {
        return "json";
    }

    @Override
    public String getMimeType() {
        return "application/json";
    }

    @Override
    public String export(Object object) {
        try {
            return this.getMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error exporting object to JSON. Error: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void export(Object object, File file) {
        try {
            this.getMapper().writeValue(file, object);
        } catch (Exception e) {
            log.error("Error exporting object to JSON file. Error: {}", e.getMessage());
        }
    }
}
