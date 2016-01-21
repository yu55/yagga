package org.yu55.yagga.handler.generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yu55.yagga.handler.api.DvcsRepository;
import org.yu55.yagga.handler.api.DvcsRepositoryFactory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class RepositoriesFileVisitor extends SimpleFileVisitor<Path> {

    private static final Logger logger = LoggerFactory.getLogger(RepositoriesFileVisitor.class);

    private String[] pathsToRepositories;

    private DvcsRepositoryFactory dvcsRepositoryFactory;

    private List<DvcsRepository> repositories;

    public RepositoriesFileVisitor(String[] pathsToRepositories, DvcsRepositoryFactory dvcsRepositoryFactory) {
        this.pathsToRepositories = pathsToRepositories;
        this.dvcsRepositoryFactory = dvcsRepositoryFactory;
        this.repositories = new LinkedList<>();
    }

    public List<DvcsRepository> getRepositories() {
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
        Optional<DvcsRepository> repository = dvcsRepositoryFactory.factorizeRepository(directory);
        if (repository.isPresent()) {
            repositories.add(repository.get());
            result = FileVisitResult.SKIP_SUBTREE;
        }
        return result;
    }
}
