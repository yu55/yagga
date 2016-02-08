package org.yu55.yagga.core.annotate.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Main class containing a content of selected file to view.
 */
public class AnnotateResponse {

    private List<AnnotateResponseLine> annotationResponseLines;

    public AnnotateResponse(List<AnnotateResponseLine> annotateResponseLines) {
        this.annotationResponseLines = annotateResponseLines;
    }

    public AnnotateResponse() {
        this.annotationResponseLines = new LinkedList<>();
    }

    public void addAnnotationResponseLine(AnnotateResponseLine line) {
        annotationResponseLines.add(line);
    }

    public List<AnnotateResponseLine> getAnnotationResponseLines() {
        return annotationResponseLines;
    }
}
