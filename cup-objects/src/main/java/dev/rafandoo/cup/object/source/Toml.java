package dev.rafandoo.cup.object.source;

import lombok.extern.slf4j.Slf4j;
import net.vieiro.toml.TOML;
import net.vieiro.toml.TOMLParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link ObjectSource} implementation for TOML documents.
 * <p>
 * This source parses TOML data into a hierarchical {@link Map}
 * structure, preserving nested sections and values.
 */
@Slf4j
public class Toml implements ObjectSource {

    @Override
    public Map<String, Object> load(InputStream input) {
        try {
            TOML toml = TOMLParser.parseFromInputStream(input);
            return toml.getRoot();
        } catch (IOException e) {
            log.error("Error loading TOML.", e);
            return new HashMap<>();
        }
    }
}
