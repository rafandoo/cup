package dev.rafandoo.cup.object.export.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import dev.rafandoo.cup.object.export.ExportStrategy;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;

/**
 * CSV export strategy.
 *
 * @see ExportStrategy
 */
@Slf4j
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
            log.error("Error exporting object to CSV. Error: {}", e.getMessage());
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
            log.error("Error exporting object to CSV file. Error: {}", e.getMessage());
        }
    }
}
