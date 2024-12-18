package br.dev.rplus.cup.object.export;

import br.dev.rplus.cup.log.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * XML export strategy.
 *
 * @see ExportStrategy
 */
public class XmlExportStrategy implements ExportStrategy {

    /**
     * Constructs a new XML export strategy.
     */
    public XmlExportStrategy() {}

    @Override
    public void export(Object object, File file) {
        XmlMapper mapper = new XmlMapper();
        try {
            String xml = mapper.writeValueAsString(object);
            FileWriter writer = new FileWriter(file, true);
            writer.write(xml);
            writer.close();
        } catch (JsonProcessingException e) {
            Logger.error("Error converting object to XML.", e);
        } catch (IOException e) {
            Logger.error("Error writing XML file.", e);
        }
    }
}
