package org.yu55.yagga.handler.api.command.grep;

import org.yu55.yagga.core.grep.model.GrepRequest;

public class GrepParameters {

    private String wanted;

    private boolean ignoreCase;

    private String fileFilter;

    public GrepParameters(String wanted, boolean ignoreCase, String fileFilter) {
        this.wanted = wanted;
        this.ignoreCase = ignoreCase;
        this.fileFilter = fileFilter;
    }

    public static GrepParameters fromRequest(GrepRequest grepRequest) {
        return new GrepParameters(grepRequest.getWanted(), grepRequest.isIgnoreCase(), grepRequest.getFileFilter());
    }

    public String getWanted() {
        return wanted;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public String getFileFilter() {
        return fileFilter;
    }
}
