package com.github.yu55.gog;

import java.util.List;

public class GrepRequest {
    private String wanted;
    private List<String> repositories;

    public String getWanted() {
        return wanted;
    }

    public List<String> getRepositories() {
        return repositories;
    }
}
