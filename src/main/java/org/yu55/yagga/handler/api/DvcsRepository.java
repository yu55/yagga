package org.yu55.yagga.handler.api;

import java.nio.file.Path;
import java.util.List;

import org.yu55.yagga.core.annotate.model.AnnotateResponse;
import org.yu55.yagga.core.grep.model.GrepResponseLine;
import org.yu55.yagga.handler.api.command.annotate.AnnotateParameters;
import org.yu55.yagga.handler.api.command.grep.GrepParameters;

public interface DvcsRepository {

    void refresh();

    AnnotateResponse annotate(AnnotateParameters annotateParameters);

    List<GrepResponseLine> grep(GrepParameters grepParameters);

    Path getDirectory();

    default String getDirectoryName() {
        return getDirectory().getFileName().toString();
    }

    default boolean isDirectoryNameEqual(String repository) {
        return getDirectoryName().equals(repository);
    }

}
