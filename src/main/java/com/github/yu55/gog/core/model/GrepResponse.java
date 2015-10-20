package com.github.yu55.gog.core.model;

import java.util.LinkedList;
import java.util.List;

public class GrepResponse {

    private final int linesLimit;

    private List<GrepResponseLine> responseLines;

    public GrepResponse(int linesLimit) {
        this.linesLimit = linesLimit;
        this.responseLines = new LinkedList<>();
    }

    public boolean isEmpty() {
        return responseLines.isEmpty();
    }

    public void add(GrepResponseLine grepResponseLine) {
        responseLines.add(grepResponseLine);
    }

    public boolean addAll(List<GrepResponseLine> grepResponseLines) {
        responseLines.addAll(grepResponseLines);
        if (grepResponseLines.size() > linesLimit) {
            responseLines = responseLines.subList(0, linesLimit);
            return false;
        }
        return true;
    }

    public List<GrepResponseLine> getResponseLines() {
        return responseLines;
    }
}
