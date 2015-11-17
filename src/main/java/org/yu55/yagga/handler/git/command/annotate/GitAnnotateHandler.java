package org.yu55.yagga.handler.git.command.annotate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.core.annotate.model.AnnotateRequest;
import org.yu55.yagga.core.annotate.model.AnnotateResponse;
import org.yu55.yagga.handler.git.GitRepositories;

@Component
public class GitAnnotateHandler {

    private GitRepositories repositories;

    @Autowired
    public GitAnnotateHandler(GitRepositories repositories) {
        this.repositories = repositories;
    }

    public AnnotateResponse annotate(AnnotateRequest annotateRequest) {
        return
                repositories.getRepositoryByDirectoryName(annotateRequest.getRepository())
                        .map(repo -> repo.annotate(annotateRequest.getFile()))
                        .get();
    }

}
