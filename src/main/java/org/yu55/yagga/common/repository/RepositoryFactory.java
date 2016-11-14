package org.yu55.yagga.common.repository;

import static java.util.stream.Collectors.toList;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory of {@link Repository} objects which can instantiate the following various types of repositories.
 */
@Component
public class RepositoryFactory {

    private final List<RepositoryResolver> repositoryResolvers;

    @Autowired
    public RepositoryFactory(List<RepositoryResolver> repositoryResolvers) {
        this.repositoryResolvers = repositoryResolvers.stream()
                .filter(resolver -> resolver.isSupported())
                .collect(toList());
    }

    /**
     * Creates a repository instance based on the specified directory.
     *
     * @param directory the repository directory
     * @return the created optional instance of repository
     */
    public Optional<Repository> factorizeRepository(Path directory) {
        return repositoryResolvers.stream()
                .filter(resolver -> resolver.isRepository(directory))
                .map(resolver -> resolver.resolveRepository(directory))
                .findFirst();
    }
}

