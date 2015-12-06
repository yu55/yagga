package org.yu55.yagga.handler.generic;

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
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.yu55.yagga.handler.api.DvcsRepository;
import org.yu55.yagga.handler.api.DvcsRepositoryFactory;

@Component
public class Repositories extends SimpleFileVisitor<Path> {

    private static final Logger logger = LoggerFactory.getLogger(Repositories.class);

    private String[] pathsToRepositories;

    private DvcsRepositoryFactory dvcsRepositoryFactory;

    private List<DvcsRepository> repositories;

    @Autowired
    public Repositories(@Value("${repositories.paths}") String[] pathsToRepositories,
                        DvcsRepositoryFactory dvcsRepositoryFactory) {
        this.pathsToRepositories = pathsToRepositories;
        this.dvcsRepositoryFactory = dvcsRepositoryFactory;
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

    @PostConstruct
    private void initRepositories() {
        repositories = new LinkedList<>();
        for (String pathToRepositories : pathsToRepositories) {
            try {
                Files.walkFileTree(Paths.get(pathToRepositories), this);
            } catch (IOException ex) {
                logger.error("Cannot obtain repositories directories", ex);
            }
        }
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
