package org.yu55.yagga.common.repository;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class RepositoriesContainer {

    private List<Repository> repositories;

    private RepositoriesFileVisitor repositoriesFileVisitor;

    public RepositoriesContainer(RepositoriesFileVisitor repositoriesFileVisitor) {
        this.repositoriesFileVisitor = repositoriesFileVisitor;
        update();
    }

    public void update() {
        repositories = repositoriesFileVisitor.getRepositories();
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    public Optional<Repository> getRepositoryByDirectoryName(String name) {
        return repositories
                .stream()
                .filter(repo -> repo.isDirectoryNameEqual(name))
                .findFirst();
    }

    public Optional<Repository> getRepositoryEndedWith(Path endingPath) {
        return repositories
                .stream()
                .filter(repo -> repo.isPathEndingWith(endingPath))
                .findFirst();
    }
}
