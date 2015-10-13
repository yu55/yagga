package com.github.yu55.gog.handler.git;

import java.io.File;

public class GitRepository {

    private final File directory;

    public GitRepository(File directory) {
        this.directory = directory;
    }

    public File getDirectory() {
        return directory;
    }
}
