package org.yu55.yagga.core.grep.model;

import java.util.StringTokenizer;

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

    public static GrepResponseLine fromGrepOutputLine(String repository, String grepOutputLine) {
        StringTokenizer stringTokenizer = new StringTokenizer(grepOutputLine, ":");
        String file = stringTokenizer.nextToken();
        int lineNumber = 0;
        try {
            lineNumber = stringTokenizer.hasMoreElements() ? Integer.parseInt(stringTokenizer.nextToken()) : 0;
        } catch (NumberFormatException e) {
        }
        String line = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken() : "";
        return new GrepResponseLine(repository, file, lineNumber, line);
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
