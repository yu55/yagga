package org.yu55.yagga.handler.api;

import org.yu55.yagga.core.annotate.model.AnnotateResponse;
import org.yu55.yagga.core.grep.model.GrepResponseLine;
import org.yu55.yagga.handler.api.command.annotate.AnnotateParameters;
import org.yu55.yagga.handler.api.command.grep.GrepParameters;

import java.io.File;
import java.util.List;

public interface DvcsRepository {

    void pull();

    AnnotateResponse annotate(AnnotateParameters annotateParameters);

    List<GrepResponseLine> grep(GrepParameters grepParameters);


    File getDirectory();

    String getDirectoryName();

    boolean isDirectoryNameEqual(String repository);

}
