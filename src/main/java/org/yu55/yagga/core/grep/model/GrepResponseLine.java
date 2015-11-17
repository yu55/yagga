package org.yu55.yagga.core.grep.model;

public class GrepResponseLine {

    private String repository;

    private String file;

    private int lineNumber;

    private String matchedTextLine;

    public GrepResponseLine(String repository, String file, int lineNumber, String matchedTextLine) {
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

    public int getLineNumber() {
        return lineNumber;
    }

    public String getMatchedTextLine() {
        return matchedTextLine;
    }
}
