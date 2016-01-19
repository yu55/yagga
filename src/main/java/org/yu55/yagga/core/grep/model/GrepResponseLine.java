package org.yu55.yagga.core.grep.model;

public class GrepResponseLine {

    private String repository;

    private String file;

    private Integer lineNumber;

    private String matchedTextLine;

    public GrepResponseLine(String repository, String file, Integer lineNumber, String matchedTextLine) {
        this.repository = repository;
        this.file = file;
        this.lineNumber = lineNumber;
        this.matchedTextLine = matchedTextLine;
    }

    public String getRepository() {
        return repository;
    }

    public String getFile() {
        return file;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public String getMatchedTextLine() {
        return matchedTextLine;
    }
}
