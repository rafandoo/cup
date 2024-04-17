package br.dev.rplus.object.export;

import br.dev.rplus.log.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvExportStrategy implements ExportStrategy {

    @Override
    public void export(Object object, File file) {
        CsvMapper mapper = new CsvMapper();
        try {
            String csv = mapper.writeValueAsString(object);
            FileWriter writer = new FileWriter(file, true);
            writer.write(csv);
            writer.close();
        } catch (JsonProcessingException e) {
            LoggerFactory.error("Error converting object to CSV: <br>%s", e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            LoggerFactory.error("Error writing CSV file: <br>%s", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
