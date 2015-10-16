package com.github.yu55.gog.core.model;

import java.util.LinkedList;
import java.util.List;

public class GrepResponse {

    public static final int RESPONSE_LINES_LIMIT = 1001;

    private List<GrepResponseLine> responseLines;

    public GrepResponse() {
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
        if (grepResponseLines.size() > RESPONSE_LINES_LIMIT) {
            responseLines = responseLines.subList(0, RESPONSE_LINES_LIMIT);
            return false;
        }
        return true;
    }

    public List<GrepResponseLine> getResponseLines() {
        return responseLines;
    }
}
