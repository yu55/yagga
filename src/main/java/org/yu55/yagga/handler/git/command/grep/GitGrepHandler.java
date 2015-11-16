package org.yu55.yagga.handler.git.command.grep;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.core.grep.model.GrepRequest;
import org.yu55.yagga.core.grep.model.GrepResponse;
import org.yu55.yagga.handler.git.GitRepositories;
import org.yu55.yagga.handler.git.GitRepository;

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

        // TODO: this should be implement in more fancy way
        for (GitRepository repository : repositories.getRepositories()) {
            if (grepRequest.hasRepository(repository.getDirectoryName())) {
                boolean addedAll = response.addAllGrepResponseLines(repository.grep(grepRequest.getWanted()));
                if (!addedAll) {
                    break;
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
