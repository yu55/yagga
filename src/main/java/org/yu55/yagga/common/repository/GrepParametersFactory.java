package org.yu55.yagga.common.repository;

import org.yu55.yagga.common.model.grep.GrepRequest;

public class GrepParametersFactory {

    public static GrepParameters factorizeGrepParameters(GrepRequest grepRequest) {
        return new GrepParameters.Builder(grepRequest.getWanted())
                .fileFilter(grepRequest.getFileFilter())
                .ignoreCase(grepRequest.isIgnoreCase())
                .onlyFilename(grepRequest.isOnlyFilename())
                .build();
    }
}
