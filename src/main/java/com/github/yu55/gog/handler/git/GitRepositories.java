package com.github.yu55.gog.handler.git;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GitRepositories {

    private static final Logger logger = LoggerFactory.getLogger(GitRepositories.class);

    private final List<GitRepository> repositories;

    @Autowired
    GitRepositories(@Value("${repositories.paths}") String[] pathsToRepositories) {
        repositories = new LinkedList<>();
        initDirectories(Arrays.asList(pathsToRepositories));
    }

    public List<GitRepository> getRepositories() {
        return repositories;
    }

    private void initDirectories(List<String> pathsToRepositories) {
        for (String ptr : pathsToRepositories) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(new File(ptr).toPath())) {
                for (Path entry : stream) {
                    if (entry.toFile().isDirectory()) {
                        repositories.add(new GitRepository(entry.toFile()));
                    }
                }
            } catch (IOException ex) {
                logger.error("Cannot obtain repositories directories", ex);
            }
        }
    }
}
