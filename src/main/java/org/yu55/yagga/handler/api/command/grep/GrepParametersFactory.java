package org.yu55.yagga.handler.api.command.grep;

import org.yu55.yagga.core.grep.model.GrepRequest;

public class GrepParametersFactory {

    public static GrepParameters factorizeGrepParameters(GrepRequest grepRequest) {
        return new GrepParameters.Builder(grepRequest.getWanted())
                .fileFilter(grepRequest.getFileFilter())
                .ignoreCase(grepRequest.isIgnoreCase())
                .onlyFilename(grepRequest.isOnlyFilename())
                .build();
    }
}
