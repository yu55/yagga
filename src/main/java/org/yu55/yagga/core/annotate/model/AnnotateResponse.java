package org.yu55.yagga.core.annotate.model;

public class AnnotateResponse {

    private String annotations;

    private String fileContent;

    public AnnotateResponse(String annotations, String fileContent) {
        this.annotations = annotations;
        this.fileContent = fileContent;
    }

    public String getAnnotations() {
        return annotations;
    }

    public String getFileContent() {
        return fileContent;
    }
}
