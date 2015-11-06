package org.yu55.yagga.core.grep.model;

import java.util.List;

public class GrepRequest {

    private String wanted;

    private List<String> repositories;

    public String getWanted() {
        return wanted;
    }

    public boolean hasRepository(String name) {
        return repositories.contains(name);
    }

    public List<String> getRepositories() {
        return repositories;
    }
}
