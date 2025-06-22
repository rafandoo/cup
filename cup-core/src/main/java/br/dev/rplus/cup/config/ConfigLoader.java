package br.dev.rplus.cup.config;

import br.dev.rplus.cup.config.source.ConfigSource;
import br.dev.rplus.cup.config.source.PropertiesConfigSource;
import br.dev.rplus.cup.config.source.YamlConfigSource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigLoader {

    public static Config from(Path path) {
        try (InputStream input = Files.newInputStream(path)) {
            return from(input, path.toString());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao abrir arquivo", e);
        }
    }

    public static Config from(String path) {
        return from(Path.of(path));
    }

    public static Config from(InputStream input, String filename) {
        ConfigSource source;
        if (filename.endsWith(".yml") || filename.endsWith(".yaml")) {
            source = new YamlConfigSource(input);
        } else if (filename.endsWith(".properties")) {
            source = new PropertiesConfigSource(input);
        } else {
            throw new IllegalArgumentException("Formato de arquivo não suportado: " + filename);
        }

        return new Config(source.load());
    }
}
