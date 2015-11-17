package org.yu55.yagga.core.grep;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.core.grep.model.GrepResponse;
import org.yu55.yagga.handler.git.command.grep.GitGrepHandler;

import org.yu55.yagga.core.grep.model.GrepRequest;

@Component
public class GrepService {

    private final GitGrepHandler gitGrepHandler;

    @Autowired
    public GrepService(GitGrepHandler gitGrepHandler) {
        this.gitGrepHandler = gitGrepHandler;
    }

    public GrepResponse grep(GrepRequest grepRequest) {
        return gitGrepHandler.grep(grepRequest);
    }

    public List<String> getAvailableRepositories() {
        return gitGrepHandler.getRepositories();
    }
}
