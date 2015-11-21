package org.yu55.yagga.core.annotate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.yu55.yagga.core.annotate.model.AnnotateRequest;
import org.yu55.yagga.core.annotate.model.AnnotateResponse;
import org.yu55.yagga.handler.api.command.annotate.AnnotateHandler;

@Component
public class AnnotateService {

    private final AnnotateHandler gitAnnotateHandler;

    @Autowired
    public AnnotateService(AnnotateHandler gitAnnotateHandler) {
        this.gitAnnotateHandler = gitAnnotateHandler;
    }

    public AnnotateResponse annotate(AnnotateRequest annotateRequest) {
        return gitAnnotateHandler.annotate(annotateRequest);
    }

}
