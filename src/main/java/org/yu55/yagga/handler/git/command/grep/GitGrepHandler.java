package org.yu55.yagga.handler.git.command.grep;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.core.grep.model.GrepRequest;
import org.yu55.yagga.core.grep.model.GrepResponse;
import org.yu55.yagga.core.grep.model.GrepResponseLine;
import org.yu55.yagga.handler.git.GitRepositories;
import org.yu55.yagga.handler.git.GitRepository;
import org.yu55.yagga.handler.git.command.common.GitCommandExecutor;
import org.yu55.yagga.handler.git.command.common.GitCommandOutput;
import org.yu55.yagga.handler.git.command.common.GitCommandOutputLine;

@Component
public class GitGrepHandler {

    private static final int RESPONSE_LINES_LIMIT = 1001;

    private GitRepositories repositories;

    @Autowired
    public GitGrepHandler(GitRepositories repositories) {
        this.repositories = repositories;
    }

    public GrepResponse grep(GrepRequest grepRequest) {

        GrepResponse response = new GrepResponse(RESPONSE_LINES_LIMIT);
        GitCommandExecutor executor = new GitCommandExecutor(new GitGrepCommand(grepRequest.getWanted()));

        // TODO: this should be implement in more fancy way
        for (GitRepository repository : repositories.getRepositories()) {
            if (grepRequest.hasRepository(repository.getName())) {
                GitCommandOutput gitCommandOutput = executor.execute(repository.getDirectory());
                if (gitCommandOutput.getExitValue() == 0) {
                    List<GitCommandOutputLine> grepOutputLines = gitCommandOutput.getOutputLines();
                    boolean addedAll = response.addAllGrepResponseLines(
                            grepOutputLines.stream().map(
                                    outputLine -> GrepResponseLine.fromGrepOutputLine(repository.getName(),
                                            outputLine.getLine())).collect(Collectors.toList()));
                    if (!addedAll) {
                        break;
                    }
                }
            }
        }

        return response;
    }

    public List<String> getRepositories() {
        return repositories.getRepositories().stream()
                .map(repo -> repo.getDirectory().getName())
                .collect(Collectors.toList());
    }
}
