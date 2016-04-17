package org.yu55.yagga.core.annotate.model;

public class AnnotateRequest {

    private String repository;

    private String file;

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
