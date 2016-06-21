package org.yu55.yagga.api.annotate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.yu55.yagga.common.model.annotate.AnnotateRequest;
import org.yu55.yagga.common.model.annotate.AnnotateResponse;
import org.yu55.yagga.common.repository.annotate.AnnotateHandler;

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
