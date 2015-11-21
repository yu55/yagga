package org.yu55.yagga.handler.api.command.grep;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.core.grep.model.GrepRequest;
import org.yu55.yagga.core.grep.model.GrepResponse;
import org.yu55.yagga.handler.Repositories;
import org.yu55.yagga.handler.api.command.grep.GrepParameters;
import org.yu55.yagga.handler.git.GitRepository;
import org.yu55.yagga.handler.git.command.grep.GitGrepCommandOptions;

@Component
public class GrepHandler {

    private static final int RESPONSE_LINES_LIMIT = 1001;

    private Repositories repositories;

    @Autowired
    public GrepHandler(Repositories repositories) {
        this.repositories = repositories;
    }

    public GrepResponse grep(GrepRequest grepRequest) {
        GrepResponse response = new GrepResponse(RESPONSE_LINES_LIMIT);

        for (GitRepository repository : repositories.getRepositories()) {
            if (grepRequest.hasRepository(repository.getDirectoryName())) {
                if (!response.addAllGrepResponseLines(
                        repository.grep(GrepParameters.fromRequest(grepRequest)))) {
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
