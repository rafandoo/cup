package br.dev.rplus.object.export;

import br.dev.rplus.log.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonExportStrategy implements ExportStrategy {

    @Override
    public void export(Object object, File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(object);
            FileWriter writer = new FileWriter(file, true);
            writer.write(json);
            writer.close();
        } catch (JsonProcessingException e) {
            LoggerFactory.error("Error converting object to JSON: <br>%s", e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            LoggerFactory.error("Error writing JSON file: <br>%s", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
