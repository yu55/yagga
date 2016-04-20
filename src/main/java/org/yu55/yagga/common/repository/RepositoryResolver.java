package org.yu55.yagga.common.repository;

import java.nio.file.Path;

/**
 * Specifies a set of method allowing for proper recognition and instantiation of {@link Repository}.
 */
public interface RepositoryResolver {

    /**
     * Checks if a source code management system is supported (e.g. whether appropriate version is installed or
     * even if required binaries exist in the system)
     *
     * @return <code>true</code> if it is supported, <code>false</code> otherwise
     */
    boolean isSupported();

    /**
     * Checks whether given directory is a repository..
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
    Repository resolveRepository(Path directory);

}
