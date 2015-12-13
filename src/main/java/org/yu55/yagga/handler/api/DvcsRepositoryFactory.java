package org.yu55.yagga.handler.api;

import static java.util.stream.Collectors.toList;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory of {@link DvcsRepository} objects which can instantiate the following various types of repositories.
 */
@Component
public class DvcsRepositoryFactory {

    private final List<DvcsRepositoryResolver> dvcsRepositoryResolvers;

    @Autowired
    public DvcsRepositoryFactory(List<DvcsRepositoryResolver> dvcsRepositoryResolvers) {
        this.dvcsRepositoryResolvers = dvcsRepositoryResolvers.stream()
                .filter(resolver -> resolver.isDvcsSupported())
                .collect(toList());
    }

    /**
     * Creates a repository instance based on the specified directory.
     *
     * @param directory the repository directory
     * @return the created optional instance of repository
     */
    public Optional<DvcsRepository> factorizeRepository(Path directory) {
        return dvcsRepositoryResolvers.stream()
                .filter(resolver -> resolver.isRepository(directory))
                .map(resolver -> resolver.resolveRepository(directory))
                .findFirst();
    }
}

