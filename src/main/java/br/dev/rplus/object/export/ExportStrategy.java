package br.dev.rplus.object.export;

import java.io.File;

public interface ExportStrategy {

    void export(Object object, File file);
}
