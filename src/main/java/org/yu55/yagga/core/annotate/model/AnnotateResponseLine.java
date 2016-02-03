package org.yu55.yagga.core.annotate.model;

/**
 * This class represents a single line of the code displayed on the view page. Each line consists of a few parts like
 * commit id, author, date, line number and line.
 */
public class AnnotateResponseLine {

    private String commitId;

    private String author;

    private String commitDate;

    private int lineNumber;

    private String line;

    public AnnotateResponseLine(String commitId, String author, String commitDate, int lineNumber,
                                String line) {
        this.commitId = commitId;
        this.author = author;
        this.commitDate = commitDate;
        this.lineNumber = lineNumber;
        this.line = line;
    }

    public AnnotateResponseLine(String line) {
        this(null, null, null, 0, line);
    }

    public String getCommitId() {
        return commitId;
    }

    public String getAuthor() {
        return author;
    }

    public String getCommitDate() {
        return commitDate;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getLine() {
        return line;
    }
}
