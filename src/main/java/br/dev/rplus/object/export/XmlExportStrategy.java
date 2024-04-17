package br.dev.rplus.object.export;

import br.dev.rplus.log.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class XmlExportStrategy implements ExportStrategy {

    @Override
    public void export(Object object, File file) {
        XmlMapper mapper = new XmlMapper();
        try {
            String xml = mapper.writeValueAsString(object);
            FileWriter writer = new FileWriter(file, true);
            writer.write(xml);
            writer.close();
        } catch (JsonProcessingException e) {
            LoggerFactory.error("Error converting object to XML: <br>%s", e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            LoggerFactory.error("Error writing XML file: <br>%s", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
