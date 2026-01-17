package dev.rafandoo.cup.object.source;

import java.io.InputStream;
import java.util.Map;

/**
 * Functional interface representing a source capable of parsing
 * structured document data into a hierarchical object model.
 */
@FunctionalInterface
public interface ObjectSource {

    /**
     * Parses structured document data from the given input stream.
     *
     * @param input an input stream containing the document data.
     * @return a map representing the root of the parsed document tree.
     */
    Map<String, Object> load(InputStream input);
}
