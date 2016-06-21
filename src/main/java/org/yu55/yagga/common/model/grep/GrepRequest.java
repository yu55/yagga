package org.yu55.yagga.common.model.grep;

import java.util.List;

public class GrepRequest {

    private String wanted;

    private List<String> repositories;

    private boolean ignoreCase;

    private boolean onlyFilename;

    private String fileFilter;

    public GrepRequest() {
        // This empty constructor is mandatory for Spring controller
    }

    GrepRequest(String wanted, List<String> repositories) {
        this.wanted = wanted;
        this.repositories = repositories;
        this.ignoreCase = false;
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

    public String getFileFilter() {
        return fileFilter;
    }

    public boolean isOnlyFilename() {
        return onlyFilename;
    }

    public static class Builder {

        private final GrepRequest grepRequest;

        public Builder(String wanted, List<String> repositories) {
            grepRequest = new GrepRequest(wanted, repositories);
        }

        public Builder ignoreCase(boolean ignoreCase) {
            grepRequest.ignoreCase = ignoreCase;
            return this;
        }

        public Builder onlyFilename(boolean onlyFilename) {
            grepRequest.onlyFilename = onlyFilename;
            return this;
        }

        public GrepRequest build() {
            return grepRequest;
        }
    }
}
