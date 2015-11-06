package org.yu55.yagga.handler.git;

import java.io.File;

/**
 * This class represents a git repository in a file system.
 */
public class GitRepository {

    private final File directory;

    public GitRepository(File directory) {
        if (directory == null) {
            throw new IllegalArgumentException("Directory cannot be null.");
        }
        this.directory = directory;
    }

    /**
     * @return the directory as an instance of {@link File} for this git repository
     */
    public File getDirectory() {
        return directory;
    }

    /**
     * @return the name of this git repository
     */
    public String getName() {
        return directory.getName();
    }
}
