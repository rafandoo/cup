package dev.rafandoo.cup;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.experimental.UtilityClass;

/**
 * This class provides factory methods for creating pre-configured Jackson ObjectMapper instances
 * for different data formats (JSON, XML, CSV). Each mapper is configured with default settings
 * to ignore unknown properties and prevent failures on null values for primitives during deserialization.
 */
@UtilityClass
public final class JacksonMapperFactory {

    /**
     * Creates and returns a default ObjectMapper instance with pre-configured settings.
     *
     * @return a default ObjectMapper instance.
     */
    public static ObjectMapper defaultMapper() {
        ObjectMapper mapper = new ObjectMapper();
        applyDefault(mapper);
        return mapper;
    }

    /**
     * Creates and returns a default ObjectMapper instance configured for JSON processing.
     *
     * @return a default ObjectMapper instance for JSON.
     */
    public static ObjectMapper json() {
        ObjectMapper mapper = new ObjectMapper();
        applyDefault(mapper);
        return mapper;
    }

    /**
     * Creates and returns a default XmlMapper instance configured for XML processing.
     *
     * @return a default XmlMapper instance for XML.
     */
    public static XmlMapper xml() {
        XmlMapper mapper = new XmlMapper();
        applyDefault(mapper);
        return mapper;
    }

    /**
     * Creates and returns a default CsvMapper instance configured for CSV processing.
     *
     * @return a default CsvMapper instance for CSV.
     */
    public static CsvMapper csv() {
        CsvMapper mapper = new CsvMapper();
        applyDefault(mapper);
        return mapper;
    }

    /**
     * Applies default configuration settings to the provided ObjectMapper instance.
     * This includes disabling failures on unknown properties and null values for primitives during deserialization.
     *
     * @param mapper the ObjectMapper instance to configure.
     */
    private static void applyDefault(ObjectMapper mapper) {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
    }
}
