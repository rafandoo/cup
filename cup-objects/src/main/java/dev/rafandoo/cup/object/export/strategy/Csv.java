package dev.rafandoo.cup.object.export.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import dev.rafandoo.cup.JacksonMapperFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * {@link ExportStrategy} implementation for exporting objects in CSV format.
 *
 * @see ExportStrategy
 */
@Slf4j
public class Csv implements JacksonExportStrategy {

    private final CsvMapper mapper;

    public Csv() {
        this(JacksonMapperFactory.csv());
    }

    public Csv(CsvMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ObjectMapper getMapper() {
        return this.mapper;
    }

    @Override
    public String getExtension() {
        return "csv";
    }

    @Override
    public String getMimeType() {
        return "text/csv";
    }

    @Override
    public String export(Object object) {
        try {
            return this.getMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error exporting object to CSV. Error: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void export(Object object, File file) {
        try {
            this.getMapper().writeValue(file, object);
        } catch (Exception e) {
            log.error("Error exporting object to CSV file. Error: {}", e.getMessage());
        }
    }
}
