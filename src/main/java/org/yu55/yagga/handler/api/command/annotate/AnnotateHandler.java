package org.yu55.yagga.handler.api.command.annotate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.core.annotate.model.AnnotateRequest;
import org.yu55.yagga.core.annotate.model.AnnotateResponse;
import org.yu55.yagga.handler.generic.Repositories;

@Component
public class AnnotateHandler {

    private Repositories repositories;

    @Autowired
    public AnnotateHandler(Repositories repositories) {
        this.repositories = repositories;
    }

    public AnnotateResponse annotate(AnnotateRequest annotateRequest) {
        return
                repositories.getRepositoryByDirectoryName(annotateRequest.getRepository())
                        .map(repo -> repo.annotate(AnnotateParameters.fromRequest(annotateRequest)))
                        .get();
    }

}
