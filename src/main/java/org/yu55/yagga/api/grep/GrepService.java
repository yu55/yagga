package org.yu55.yagga.api.grep;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.api.grep.model.GrepResponse;
import org.yu55.yagga.common.handler.GrepHandler;

import org.yu55.yagga.api.grep.model.GrepRequest;

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
