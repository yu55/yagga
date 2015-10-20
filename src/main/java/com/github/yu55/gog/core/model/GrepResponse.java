package com.github.yu55.gog.core.model;

import java.util.LinkedList;
import java.util.List;

public class GrepResponse {

    private final int linesLimit;

    private List<GrepResponseLine> grepResponseLines;

    public GrepResponse(int linesLimit) {
        this.linesLimit = linesLimit;
        this.grepResponseLines = new LinkedList<>();
    }

    public boolean isEmpty() {
        return grepResponseLines.isEmpty();
    }

    public void add(GrepResponseLine grepResponseLine) {
        grepResponseLines.add(grepResponseLine);
    }

    public boolean addAll(List<GrepResponseLine> grepResponseLines) {
        this.grepResponseLines.addAll(grepResponseLines);
        if (grepResponseLines.size() > linesLimit) {
            this.grepResponseLines = this.grepResponseLines.subList(0, RESPONSE_LINES_LIMIT);
            return false;
        }
        return true;
    }

    public List<GrepResponseLine> getGrepResponseLines() {
        return grepResponseLines;
    }
}
