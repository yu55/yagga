package org.yu55.yagga.common.repository.annotate;

import org.yu55.yagga.common.model.annotate.AnnotateRequest;

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
