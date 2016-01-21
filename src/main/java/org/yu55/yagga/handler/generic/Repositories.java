package org.yu55.yagga.handler.generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yu55.yagga.handler.api.DvcsRepository;

import java.util.List;
import java.util.Optional;

public class Repositories {

    private static final Logger logger = LoggerFactory.getLogger(Repositories.class);

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

}
