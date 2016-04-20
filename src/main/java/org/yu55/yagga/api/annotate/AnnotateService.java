package org.yu55.yagga.api.annotate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.yu55.yagga.api.annotate.model.AnnotateRequest;
import org.yu55.yagga.api.annotate.model.AnnotateResponse;
import org.yu55.yagga.common.handler.AnnotateHandler;

@Component
public class AnnotateService {

    private final AnnotateHandler annotateHandler;

    @Autowired
    public AnnotateService(AnnotateHandler annotateHandler) {
        this.annotateHandler = annotateHandler;
    }

    public AnnotateResponse annotate(AnnotateRequest annotateRequest) {
        return annotateHandler.annotate(annotateRequest);
    }

}
