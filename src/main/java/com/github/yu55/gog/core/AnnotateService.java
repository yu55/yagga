package com.github.yu55.gog.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.yu55.gog.core.model.AnnotateRequest;
import com.github.yu55.gog.core.model.AnnotateResponse;
import com.github.yu55.gog.handler.git.GitAnnotateHandler;

@Component
public class AnnotateService {

    private final GitAnnotateHandler gitAnnotateHandler;

    @Autowired
    public AnnotateService(GitAnnotateHandler gitAnnotateHandler) {
        this.gitAnnotateHandler = gitAnnotateHandler;
    }

    public AnnotateResponse annotate(AnnotateRequest annotateRequest) {
        return gitAnnotateHandler.annotate(annotateRequest);
    }

}
