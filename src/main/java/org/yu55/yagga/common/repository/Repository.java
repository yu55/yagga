package org.yu55.yagga.common.repository;

import java.nio.file.Path;
import java.util.List;

import org.yu55.yagga.api.annotate.model.AnnotateResponse;
import org.yu55.yagga.api.grep.model.GrepResponseLine;
import org.yu55.yagga.common.parameters.AnnotateParameters;
import org.yu55.yagga.common.parameters.GrepParameters;

public interface Repository {

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

    default boolean isPathEndingWith(Path endingPath) {
        return getDirectory().endsWith(endingPath);
    }
}
