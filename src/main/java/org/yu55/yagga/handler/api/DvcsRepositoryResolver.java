package org.yu55.yagga.handler.api;

import java.nio.file.Path;

/**
 * Specifies a set of method allowing for proper recognition and instantiation of {@link DvcsRepository}.
 */
public interface DvcsRepositoryResolver {

    /**
     * Checks if a dvcs is supported (e.g. whether appropriate version is installed or even if dvcs exists in the system)
     *
     * @return <code>true</code> if the dvcs is supported, <code>false</code> otherwise
     */
    boolean isDvcsSupported();

    /**
     * Checks whether given directory is a dvcs repository.
     *
     * @param directory the directory to be checked
     * @return <code>true</code> if the directory is a repository; <code>false</code> otherwise
     */
    boolean isRepository(Path directory);

    /**
     * Creates a dvcs repository instance based on the specified directory.
     *
     * @param directory the repository directory
     * @return the created instance of repository
     */
    DvcsRepository resolveRepository(Path directory);

}
