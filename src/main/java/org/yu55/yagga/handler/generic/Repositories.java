package org.yu55.yagga.handler.generic;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.yu55.yagga.handler.api.DvcsRepository;

public class Repositories {

    private List<DvcsRepository> repositories;

    private RepositoriesFileVisitor repositoriesFileVisitor;

    public Repositories(RepositoriesFileVisitor repositoriesFileVisitor) {
        this.repositoriesFileVisitor = repositoriesFileVisitor;
        update();
    }

    public void update() {
        repositories = repositoriesFileVisitor.getRepositories();
    }

    public List<DvcsRepository> getRepositories() {
        return repositories;
    }

    public Optional<DvcsRepository> getRepositoryByDirectoryName(String name) {
        return repositories
                .stream()
                .filter(repo -> repo.isDirectoryNameEqual(name))
                .findFirst();
    }

    public Optional<DvcsRepository> getRepositoryEndedWith(Path endingPath) {
        return repositories
                .stream()
                .filter(repo -> repo.isPathEndingWith(endingPath))
                .findFirst();
    }
}
