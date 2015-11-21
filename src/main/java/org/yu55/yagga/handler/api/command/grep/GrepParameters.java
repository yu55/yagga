package org.yu55.yagga.handler.api.command.grep;

import org.yu55.yagga.core.grep.model.GrepRequest;
import org.yu55.yagga.handler.git.command.grep.GitGrepCommandOptions;

public class GrepParameters {

    private String wanted;
    private boolean ignoreCase;

    public GrepParameters(String wanted, boolean ignoreCase) {
        this.wanted = wanted;
        this.ignoreCase = ignoreCase;
    }

    public String getWanted() {
        return wanted;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public static GrepParameters fromRequest(GrepRequest grepRequest) {
        return new GrepParameters(grepRequest.getWanted(), grepRequest.isIgnoreCase());
    }
}
