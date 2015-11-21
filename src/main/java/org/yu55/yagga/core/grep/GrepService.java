package org.yu55.yagga.core.grep;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.core.grep.model.GrepResponse;
import org.yu55.yagga.handler.api.command.grep.GrepHandler;

import org.yu55.yagga.core.grep.model.GrepRequest;

@Component
public class GrepService {

    private final GrepHandler gitGrepHandler;

    @Autowired
    public GrepService(GrepHandler gitGrepHandler) {
        this.gitGrepHandler = gitGrepHandler;
    }

    public GrepResponse grep(GrepRequest grepRequest) {
        return gitGrepHandler.grep(grepRequest);
    }

    public List<String> getAvailableRepositories() {
        return gitGrepHandler.getRepositories();
    }
}
