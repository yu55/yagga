package com.github.yu55.gog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

@Component
public class Repositories {

    private static final Logger logger = LoggerFactory.getLogger(Repositories.class);

    private final String pathToRepositories;

    private final List<File> directories;

    @Autowired
    Repositories(@Value("${repositories.path}") String pathToRepositories) {
        this.pathToRepositories = pathToRepositories;
        directories = new LinkedList<>();
    }

    List<File> getDirectories() {
        if (directories.isEmpty()) {
            initDirectories();
        }
        return directories;
    }

    private void initDirectories() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(new File(pathToRepositories).toPath())) {
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
