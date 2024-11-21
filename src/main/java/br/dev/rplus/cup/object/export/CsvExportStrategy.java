package br.dev.rplus.cup.object.export;

import br.dev.rplus.cup.log.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * CSV export strategy.
 *
 * @see ExportStrategy
 */
public class CsvExportStrategy implements ExportStrategy {

    /**
     * Constructs a new CSV export strategy.
     */
    public CsvExportStrategy() {}

    @Override
    public void export(Object object, File file) {
        CsvMapper mapper = new CsvMapper();
        try {
            String csv = mapper.writeValueAsString(object);
            FileWriter writer = new FileWriter(file, true);
            writer.write(csv);
            writer.close();
        } catch (JsonProcessingException e) {
            Logger.error("Error converting object to CSV.", e);
        } catch (IOException e) {
            Logger.error("Error writing CSV file.", e);
        }
    }
}
