package com.github.yu55.gog.handler.git;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.yu55.gog.core.model.GrepRequest;
import com.github.yu55.gog.core.model.GrepResponse;
import com.github.yu55.gog.core.model.GrepResponseLine;
import com.github.yu55.gog.handler.git.command.GitCommandExecutor;
import com.github.yu55.gog.handler.git.command.GitGrepCommand;

@Component
public class GitGrepHandler {

    private static final int RESPONSE_LINES_LIMIT = 1001;

    private GitRepositories repositories;

    @Autowired
    public GitGrepHandler(GitRepositories repositories) {
        this.repositories = repositories;
    }

    public GrepResponse grep(GrepRequest wanted) {

        GrepResponse response = new GrepResponse(RESPONSE_LINES_LIMIT);
        GitCommandExecutor executor = new GitCommandExecutor(new GitGrepCommand(wanted.getWanted()));

        for (GitRepository repository : repositories.getRepositories()) {
            File directory = repository.getDirectory();
            if (wanted.hasRepository(directory.getName())) {
                List<String> outputLines = executor.execute(directory);
                boolean addedAll = response.addAll(
                        outputLines.stream().map(string -> new GrepResponseLine(string)).collect(
                                Collectors.toList()));
                if (!addedAll) {
                    response.add(new GrepResponseLine(
                            String.format("More results than %d. Aborting...", RESPONSE_LINES_LIMIT)));
                    break;
                }
            }
        }

        if (response.isEmpty()) {
            response.add(new GrepResponseLine("No findings in selected repositories."));
        }

        return response;
    }

    public List<String> getRepositories() {
        return repositories.getRepositories().stream()
                .map(repo -> repo.getDirectory().getName())
                .collect(Collectors.toList());
    }
}
