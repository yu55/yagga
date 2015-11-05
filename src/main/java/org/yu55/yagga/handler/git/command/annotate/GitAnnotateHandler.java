package org.yu55.yagga.handler.git.command.annotate;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.core.annotate.model.AnnotateRequest;
import org.yu55.yagga.handler.git.GitRepositories;
import org.yu55.yagga.handler.git.GitRepository;
import org.yu55.yagga.handler.git.command.common.GitCommandExecutor;
import org.yu55.yagga.handler.git.command.common.GitCommandOutputLine;

import org.yu55.yagga.core.annotate.model.AnnotateResponse;

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
        List<String> annotatedOutputLines = gitCommandExecutor.execute(gitRepository.getDirectory()).getOutputLines()
                .stream()
                .map(GitCommandOutputLine::getLine)
                .collect(Collectors.toList());

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
