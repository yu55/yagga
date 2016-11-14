package org.yu55.yagga.common.repository.grep;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.common.model.grep.GrepRequest;
import org.yu55.yagga.common.model.grep.GrepResponse;
import org.yu55.yagga.common.repository.RepositoriesContainer;
import org.yu55.yagga.common.repository.Repository;

@Component
public class GrepHandler {

    private static final int RESPONSE_LINES_LIMIT = 1001;

    private RepositoriesContainer repositoriesContainer;

    @Autowired
    public GrepHandler(RepositoriesContainer repositoriesContainer) {
        this.repositoriesContainer = repositoriesContainer;
    }

    public GrepResponse grep(GrepRequest grepRequest) {
        GrepResponse response = new GrepResponse(RESPONSE_LINES_LIMIT);

        for (Repository repository : repositoriesContainer.getRepositories()) {
            if (grepRequest.hasRepository(repository.getDirectoryName())) {
                if (!response.addAllGrepResponseLines(
                        repository.grep(GrepParametersFactory.factorizeGrepParameters(grepRequest)))) {
                    break;
                }
            }
        }

        return response;
    }

    public List<String> getRepositories() {
        return repositoriesContainer.getRepositories().stream()
                .map(Repository::getDirectoryName)
                .collect(Collectors.toList());
    }
}
