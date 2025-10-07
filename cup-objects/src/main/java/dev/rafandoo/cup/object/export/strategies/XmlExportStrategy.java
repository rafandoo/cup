package dev.rafandoo.cup.object.export.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import dev.rafandoo.cup.object.export.ExportStrategy;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;

/**
 * XML export strategy.
 *
 * @see ExportStrategy
 */
@Slf4j
@NoArgsConstructor
public class XmlExportStrategy implements ExportStrategy {

    @Override
    public String getExtension() {
        return "xml";
    }

    @Override
    public String getContentType() {
        return "application/xml";
    }

    @Override
    public String export(Object object) {
        XmlMapper mapper = new XmlMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error exporting object to XML. Error: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void export(Object object, File file) {
        try {
            String xml = this.export(object);
            FileWriter writer = new FileWriter(file, false);
            writer.write(xml);
            writer.close();
        } catch (Exception e) {
            log.error("Error exporting object to XML file. Error: {}", e.getMessage());
        }
    }
}
