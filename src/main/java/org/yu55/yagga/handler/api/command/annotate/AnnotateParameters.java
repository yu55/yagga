package org.yu55.yagga.handler.api.command.annotate;

import org.yu55.yagga.core.annotate.model.AnnotateRequest;

public class AnnotateParameters {
    private String file;

    public AnnotateParameters(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }

    public static AnnotateParameters fromRequest(AnnotateRequest annotateRequest) {
        return new AnnotateParameters(annotateRequest.getFile());
    }
}
