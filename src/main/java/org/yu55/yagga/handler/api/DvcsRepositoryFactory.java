package org.yu55.yagga.handler.api;

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

    private final List<DvcsRepositoryDescriptor> repositoryDescriptors;

    @Autowired
    public DvcsRepositoryFactory(List<DvcsRepositoryDescriptor> repositoryDescriptors) {
        this.repositoryDescriptors = repositoryDescriptors;
    }

    /**
     * Creates a repository instance based on the specified directory.
     *
     * @param directory the repository directory
     * @return the created optional instance of repository
     */
    public Optional<DvcsRepository> factorizeRepository(Path directory) {
        return repositoryDescriptors.stream()
                .filter(descriptor -> descriptor.isRepository(directory))
                .map(descriptor -> descriptor.createRepository(directory))
                .findFirst();
    }
}

