package org.yu55.yagga.core.grep.model;

import java.util.List;

public class GrepRequest {

    private String wanted;

    private List<String> repositories;

    private boolean ignoreCase;

    public GrepRequest() {
        // This empty constructor is mandatory for Spring controller
    }

    public GrepRequest(String wanted, List<String> repositories, boolean ignoreCase) {
        this.wanted = wanted;
        this.repositories = repositories;
        this.ignoreCase = ignoreCase;
    }

    public String getWanted() {
        return wanted;
    }

    public boolean hasRepository(String name) {
        return repositories.contains(name);
    }

    public List<String> getRepositories() {
        return repositories;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }
}
