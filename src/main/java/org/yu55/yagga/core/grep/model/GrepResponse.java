package org.yu55.yagga.core.grep.model;

import java.util.LinkedList;
import java.util.List;

public class GrepResponse {

    private final int grepLinesLimit;

    private List<GrepResponseLine> grepResponseLines;

    public GrepResponse(int grepLinesLimit) {
        this.grepLinesLimit = grepLinesLimit;
        this.grepResponseLines = new LinkedList<>();
    }

    public boolean addAllGrepResponseLines(List<GrepResponseLine> grepResponseLines) {
        this.grepResponseLines.addAll(grepResponseLines);
        if (grepResponseLines.size() > grepLinesLimit) {
            this.grepResponseLines = this.grepResponseLines.subList(0, grepLinesLimit);
            return false;
        }
        return true;
    }

    public List<GrepResponseLine> getGrepResponseLines() {
        return grepResponseLines;
    }
}
