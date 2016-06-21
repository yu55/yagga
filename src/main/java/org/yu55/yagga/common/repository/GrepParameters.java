package org.yu55.yagga.common.repository;

public class GrepParameters {

    private String wanted;

    private boolean ignoreCase;

    private boolean onlyFilename;

    private String fileFilter;

    GrepParameters(String wanted) {
        this.wanted = wanted;
        this.ignoreCase = false;
        this.onlyFilename = false;
    }

    public String getWanted() {
        return wanted;
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

        private final GrepParameters grepParameters;

        public Builder(String wanted) {
            this.grepParameters = new GrepParameters(wanted);
        }

        public Builder ignoreCase(boolean ignoreCase) {
            grepParameters.ignoreCase = ignoreCase;
            return this;
        }

        public Builder fileFilter(String fileFilter) {
            grepParameters.fileFilter = fileFilter;
            return this;
        }

        public Builder onlyFilename(boolean onlyFilename) {
            grepParameters.onlyFilename = onlyFilename;
            return this;
        }

        public GrepParameters build() {
            return grepParameters;
        }
    }
}
