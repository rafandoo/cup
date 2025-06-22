package br.dev.rplus.cup.object.export.strategies;

import br.dev.rplus.cup.log.Logger;
import br.dev.rplus.cup.object.export.ExportStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileWriter;

/**
 * CSV export strategy.
 *
 * @see ExportStrategy
 */
@NoArgsConstructor
public class CsvExportStrategy implements ExportStrategy {

    @Override
    public String getExtension() {
        return "csv";
    }

    @Override
    public String getContentType() {
        return "text/csv";
    }

    @Override
    public String export(Object object) {
        CsvMapper mapper = new CsvMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            Logger.getInstance().error("Error converting object to CSV.", e);
            return null;
        }
    }

    @Override
    public void export(Object object, File file) {
        try {
            String csv = this.export(object);
            FileWriter writer = new FileWriter(file, false);
            writer.write(csv);
            writer.close();
        } catch (Exception e) {
            Logger.getInstance().error("Error writing CSV file.", e);
        }
    }
}
