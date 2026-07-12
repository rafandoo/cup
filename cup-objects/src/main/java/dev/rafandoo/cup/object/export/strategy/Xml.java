package dev.rafandoo.cup.object.export.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import dev.rafandoo.cup.JacksonMapperFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * {@link ExportStrategy} implementation for exporting objects in XML format.
 *
 * @see ExportStrategy
 */
@Slf4j
public class Xml implements JacksonExportStrategy {

    private final XmlMapper mapper;

    public Xml() {
        this(JacksonMapperFactory.xml());
    }

    public Xml (XmlMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ObjectMapper getMapper() {
        return this.mapper;
    }

    @Override
    public String getExtension() {
        return "xml";
    }

    @Override
    public String getMimeType() {
        return "application/xml";
    }

    @Override
    public String export(Object object) {
        try {
            return this.getMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error exporting object to XML. Error: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void export(Object object, File file) {
        try {
            this.getMapper().writeValue(file, object);
        } catch (Exception e) {
            log.error("Error exporting object to XML file. Error: {}", e.getMessage());
        }
    }
}
