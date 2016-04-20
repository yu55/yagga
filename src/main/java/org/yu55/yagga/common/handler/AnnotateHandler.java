package org.yu55.yagga.common.handler;

import static java.text.MessageFormat.format;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.api.annotate.model.AnnotateRequest;
import org.yu55.yagga.api.annotate.model.AnnotateResponse;
import org.yu55.yagga.common.parameters.AnnotateParameters;
import org.yu55.yagga.common.repository.RepositoriesContainer;

@Component
public class AnnotateHandler {

    private RepositoriesContainer repositoriesContainer;

    @Autowired
    public AnnotateHandler(RepositoriesContainer repositoriesContainer) {
        this.repositoriesContainer = repositoriesContainer;
    }

    public AnnotateResponse annotate(AnnotateRequest annotateRequest) {
        return
                repositoriesContainer.getRepositoryByDirectoryName(annotateRequest.getRepository())
                        .map(repo -> repo.annotate(AnnotateParameters.fromRequest(annotateRequest)))
                        .orElseThrow(() -> new IllegalArgumentException(
                                format("Repository {0} not found", annotateRequest.getRepository())));
    }

}
