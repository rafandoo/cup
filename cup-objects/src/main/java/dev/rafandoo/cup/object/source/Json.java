package dev.rafandoo.cup.object.source;

import dev.rafandoo.cup.object.tree.ObjectTree;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.Map;

/**
 * {@link ObjectSource} implementation for JSON documents.
 * <p>
 * This source parses JSON data from an {@link InputStream} and converts it
 * into a {@link Map}-based representation compatible with {@link ObjectTree}.
 */
public class Json implements ObjectSource {

    @Override
    public Map<String, Object> load(InputStream input) {
        JSONObject object = new JSONObject(new JSONTokener(input));
        return object.toMap();
    }
}
