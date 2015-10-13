package com.github.yu55.gog.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.yu55.gog.core.model.GrepRequest;
import com.github.yu55.gog.core.model.GrepResponse;
import com.github.yu55.gog.handler.git.GitGrepHandler;

@Component
public class GrepService {

    private final GitGrepHandler gitGrepHandler;

    @Autowired
    public GrepService(GitGrepHandler gitGrepHandler) {
        this.gitGrepHandler = gitGrepHandler;
    }

    public GrepResponse grep(GrepRequest wanted) {
        return gitGrepHandler.grep(wanted);
    }

    public List<String> getAvailavleRepositories() {
        return gitGrepHandler.getRepositories();
    }
}
