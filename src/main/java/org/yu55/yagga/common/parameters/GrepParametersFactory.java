package org.yu55.yagga.common.parameters;

import org.yu55.yagga.api.grep.model.GrepRequest;

public class GrepParametersFactory {

    public static GrepParameters factorizeGrepParameters(GrepRequest grepRequest) {
        return new GrepParameters.Builder(grepRequest.getWanted())
                .fileFilter(grepRequest.getFileFilter())
                .ignoreCase(grepRequest.isIgnoreCase())
                .onlyFilename(grepRequest.isOnlyFilename())
                .build();
    }
}
