package org.yu55.yagga.handler.api;

import java.nio.file.Path;

public interface DvcsRepositoryDescriptor {

    /**
     * Checks whether given directory contains a source code repository.
     *
     * @param directory the directory to be checked
     * @return <code>true</code> if the directory is a repository; <code>false</code> otherwise
     */
    boolean isRepository(Path directory);

    /**
     * Creates a repository instance based on the specified directory.
     *
     * @param directory the repository directory
     * @return the created instance of repository
     */
    DvcsRepository createRepository(Path directory);

}
