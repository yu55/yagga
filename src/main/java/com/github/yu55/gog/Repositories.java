package com.github.yu55.gog;

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
public class Repositories {

    private static final Logger logger = LoggerFactory.getLogger(Repositories.class);

    private final List<String> pathsToRepositories;

    private final List<File> directories;

    @Autowired
    Repositories(@Value("${repositories.paths}") String[] pathsToRepositories) {
        this.pathsToRepositories = Arrays.asList(pathsToRepositories);
        directories = new LinkedList<>();
    }

    List<File> getDirectories() {
        if (directories.isEmpty()) {
            initDirectories();
        }
        return directories;
    }

    private void initDirectories() {
        for (String ptr : pathsToRepositories) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(new File(ptr).toPath())) {
                for (Path entry : stream) {
                    if (entry.toFile().isDirectory()) {
                        directories.add(entry.toFile());
                    }
                }
            } catch (IOException ex) {
                logger.error("Cannot obtain repositories directories", ex);
            }
        }
    }
}
