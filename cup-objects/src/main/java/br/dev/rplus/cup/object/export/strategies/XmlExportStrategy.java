package br.dev.rplus.cup.object.export.strategies;

import br.dev.rplus.cup.log.Logger;
import br.dev.rplus.cup.object.export.ExportStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileWriter;

/**
 * XML export strategy.
 *
 * @see ExportStrategy
 */
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
            Logger.getInstance().error("Error converting object to XML.", e);
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
            Logger.getInstance().error("Error writing XML file.", e);
        }
    }
}
