package org.yu55.yagga.handler.generic;

import static java.nio.file.Files.newDirectoryStream;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.yu55.yagga.handler.api.DvcsRepository;
import org.yu55.yagga.handler.api.DvcsRepositoryFactory;
import org.yu55.yagga.handler.api.DvcsRepositoryFactory.RepositoryInstantiationException;

@Component
public class Repositories {

    private static final Logger logger = LoggerFactory.getLogger(Repositories.class);

    private List<DvcsRepository> repositories;

    @Autowired
    public Repositories(@Value("${repositories.paths}") String[] pathsToRepositories,
                        DvcsRepositoryFactory dvcsRepositoryFactory) {
        this.repositories = initRepositories(pathsToRepositories, dvcsRepositoryFactory);

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

    private List<DvcsRepository> initRepositories(String[] pathsToRepositories,
                                                  DvcsRepositoryFactory dvcsRepositoryFactory) {
        List<DvcsRepository> repositories = new LinkedList<>();
        for (String ptr : pathsToRepositories) {
            try (DirectoryStream<Path> stream = newDirectoryStream(new File(ptr).toPath())) {
                for (Path entry : stream) {
                    try {
                        repositories.add(dvcsRepositoryFactory.factorizeRepository(entry.toFile()));
                    } catch (RepositoryInstantiationException ex) {
                        logger.info(ex.getMessage());
                    }
                }
            } catch (IOException ex) {
                logger.error("Cannot obtain repositories directories", ex);
            }
        }
        return repositories;
    }

}
