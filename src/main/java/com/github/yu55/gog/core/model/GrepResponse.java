package com.github.yu55.gog.core.model;

import java.util.LinkedList;
import java.util.List;

public class GrepResponse {

    private static final int RESPONSE_LINES_LIMIT = 1001;

    private List<GrepResponseLine> grepResponseLines;

    public GrepResponse() {
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
        if (grepResponseLines.size() > RESPONSE_LINES_LIMIT) {
            this.grepResponseLines = this.grepResponseLines.subList(0, RESPONSE_LINES_LIMIT);
            return false;
        }
        return true;
    }

    public List<GrepResponseLine> getGrepResponseLines() {
        return grepResponseLines;
    }
}
