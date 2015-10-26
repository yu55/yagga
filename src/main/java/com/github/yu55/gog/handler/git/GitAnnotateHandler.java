package com.github.yu55.gog.handler.git;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.yu55.gog.core.model.AnnotateRequest;
import com.github.yu55.gog.core.model.AnnotateResponse;
import com.github.yu55.gog.handler.git.command.GitAnnotateCommand;
import com.github.yu55.gog.handler.git.command.GitCommandExecutor;

@Component
public class GitAnnotateHandler {

    private GitRepositories repositories;

    @Autowired
    public GitAnnotateHandler(GitRepositories repositories) {
        this.repositories = repositories;
    }

    public AnnotateResponse annotate(AnnotateRequest annotateRequest) {

        GitCommandExecutor executor = new GitCommandExecutor(new GitAnnotateCommand(annotateRequest.getFile()));

        return repositories.getRepositories().stream()
                .filter(gitRepository -> gitRepository.getDirectory().getName().equals(annotateRequest.getRepository()))
                .findFirst()
                .map(foundGitRepository -> executeAndConvertToResponse(foundGitRepository, executor))
                .get();
    }

    private AnnotateResponse executeAndConvertToResponse(GitRepository gitRepository,
                                                         GitCommandExecutor gitCommandExecutor) {
        List<String> annotatedOutputLines = gitCommandExecutor.execute(gitRepository.getDirectory());

        StringBuilder annotationsStringBuilder = new StringBuilder();
        StringBuilder fileContentStringBuilder = new StringBuilder();

        for (String annotatedLine: annotatedOutputLines) {
            int splitIndex = annotatedLine.indexOf(")");
            annotationsStringBuilder.append(annotatedLine.substring(0, splitIndex + 1)).append("\n");
            fileContentStringBuilder.append(annotatedLine.substring(splitIndex + 1)).append("\n");
        }

        return new AnnotateResponse(annotationsStringBuilder.toString(), fileContentStringBuilder.toString());
    }

}
