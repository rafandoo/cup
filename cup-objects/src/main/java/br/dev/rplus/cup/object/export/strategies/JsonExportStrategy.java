package br.dev.rplus.cup.object.export.strategies;

import br.dev.rplus.cup.log.Logger;
import br.dev.rplus.cup.object.export.ExportStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileWriter;

/**
 * JSON export strategy.
 *
 * @see ExportStrategy
 */
@NoArgsConstructor
public class JsonExportStrategy implements ExportStrategy {

    @Override
    public String getExtension() {
        return "json";
    }

    @Override
    public String getContentType() {
        return "application/json";
    }

    @Override
    public String export(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            Logger.getInstance().error("Error converting object to JSON.", e);
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
            Logger.getInstance().error("Error writing JSON file.", e);
        }
    }
}
