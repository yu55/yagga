package com.github.yu55.gog.core.model;

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
