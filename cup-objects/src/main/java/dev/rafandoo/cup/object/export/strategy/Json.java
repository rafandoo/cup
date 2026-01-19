package dev.rafandoo.cup.object.export.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;

/**
 * {@link ExportStrategy} implementation for exporting objects in JSON format.
 *
 * @see ExportStrategy
 */
@Slf4j
@NoArgsConstructor
public class Json implements ExportStrategy {

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
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error exporting object to JSON. Error: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void export(Object object, File file) {
        try {
            String json = this.export(object);
            FileWriter writer = new FileWriter(file, false);
            writer.write(json);
            writer.close();
        } catch (Exception e) {
            log.error("Error exporting object to JSON file. Error: {}", e.getMessage());
        }
    }
}
