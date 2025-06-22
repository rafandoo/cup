package br.dev.rplus.cup.config.source;

import java.util.Map;

@FunctionalInterface
public interface ConfigSource {

    Map<String, Object> load();
}
