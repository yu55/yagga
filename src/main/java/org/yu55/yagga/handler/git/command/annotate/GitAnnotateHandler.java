package org.yu55.yagga.handler.git.command.annotate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.core.annotate.model.AnnotateRequest;
import org.yu55.yagga.core.annotate.model.AnnotateResponse;
import org.yu55.yagga.handler.git.GitRepositories;
import org.yu55.yagga.handler.git.GitRepository;
import org.yu55.yagga.handler.git.command.common.GitCommandExecutor;
import org.yu55.yagga.handler.git.command.common.GitCommandOutput;
import org.yu55.yagga.handler.git.command.common.GitCommandOutputLine;

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
        GitCommandOutput gitCommandOutput = gitCommandExecutor.execute(
                gitRepository.getDirectory());
        List<GitCommandOutputLine> outputLines = gitCommandOutput.getOutputLines();

        StringBuilder annotationsStringBuilder = new StringBuilder();
        StringBuilder fileContentStringBuilder = new StringBuilder();

        // TODO: this should be done smarter
        for (GitCommandOutputLine outputLine: outputLines) {
            String annotatedLine = outputLine.getLine();
            if (gitCommandOutput.getExitValue() == 0) {
                int splitIndex = annotatedLine.indexOf(")");
                annotationsStringBuilder.append(annotatedLine.substring(0, splitIndex + 1)).append("\n");
                fileContentStringBuilder.append(annotatedLine.substring(splitIndex + 1)).append("\n");
            } else {
                annotationsStringBuilder.append(annotatedLine).append("\n");
            }
        }

        return new AnnotateResponse(annotationsStringBuilder.toString(), fileContentStringBuilder.toString());
    }

}
