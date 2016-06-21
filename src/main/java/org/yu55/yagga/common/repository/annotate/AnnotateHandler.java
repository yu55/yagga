package org.yu55.yagga.common.repository.annotate;

import static java.text.MessageFormat.format;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.common.model.annotate.AnnotateRequest;
import org.yu55.yagga.common.model.annotate.AnnotateResponse;
import org.yu55.yagga.common.repository.AnnotateParameters;
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
