package org.yu55.yagga.api.grep;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.common.model.grep.GrepRequest;
import org.yu55.yagga.common.model.grep.GrepResponse;
import org.yu55.yagga.common.repository.grep.GrepHandler;

@Component
public class GrepService {

    private final GrepHandler grepHandler;

    @Autowired
    public GrepService(GrepHandler grepHandler) {
        this.grepHandler = grepHandler;
    }

    public GrepResponse grep(GrepRequest grepRequest) {
        return grepHandler.grep(grepRequest);
    }

    public List<String> getAvailableRepositories() {
        return grepHandler.getRepositories();
    }
}
