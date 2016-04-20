package org.yu55.yagga.common.repository;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepositoriesFileVisitor extends SimpleFileVisitor<Path> {

    private static final Logger logger = LoggerFactory.getLogger(RepositoriesFileVisitor.class);

    private String[] pathsToRepositories;

    private RepositoryFactory repositoryFactory;

    private List<Repository> repositories;

    public RepositoriesFileVisitor(String[] pathsToRepositories, RepositoryFactory repositoryFactory) {
        this.pathsToRepositories = pathsToRepositories;
        this.repositoryFactory = repositoryFactory;
        this.repositories = new LinkedList<>();
    }

    public List<Repository> getRepositories() {
        repositories.clear();
        for (String pathToRepositories : pathsToRepositories) {
            try {
                Files.walkFileTree(Paths.get(pathToRepositories), this);
            } catch (IOException ex) {
                logger.error("Cannot obtain repositories directories", ex);
            }
        }
        return new LinkedList<>(repositories);
    }

    @Override
    public FileVisitResult preVisitDirectory(Path directory, BasicFileAttributes attrs) throws IOException {
        FileVisitResult result = super.preVisitDirectory(directory, attrs);
        Optional<Repository> repository = repositoryFactory.factorizeRepository(directory);
        if (repository.isPresent()) {
            repositories.add(repository.get());
            result = FileVisitResult.SKIP_SUBTREE;
        }
        return result;
    }
}
