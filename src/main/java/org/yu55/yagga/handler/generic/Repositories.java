package org.yu55.yagga.handler.generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.yu55.yagga.handler.api.DvcsRepository;
import org.yu55.yagga.handler.git.GitRepository;
import org.yu55.yagga.handler.git.command.common.GitCommandExecutorFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class Repositories {

    private static final Logger logger = LoggerFactory.getLogger(Repositories.class);

    private final List<DvcsRepository> repositories;

    private GitCommandExecutorFactory gitCommandExecutorFactory;

    @Autowired
    public Repositories(@Value("${repositories.paths}") String[] pathsToRepositories,
                        GitCommandExecutorFactory gitCommandExecutorFactory) {
        this.gitCommandExecutorFactory = gitCommandExecutorFactory;
        repositories = new LinkedList<>();
        initDirectories(Arrays.asList(pathsToRepositories));

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

    private void initDirectories(List<String> pathsToRepositories) {
        for (String ptr : pathsToRepositories) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(new File(ptr).toPath())) {
                for (Path entry : stream) {
                    if (isGitRepository(entry)) {
                        repositories.add(new GitRepository(entry.toFile(), gitCommandExecutorFactory));
                    }
                }
            } catch (IOException ex) {
                logger.error("Cannot obtain repositories directories", ex);
            }
        }
    }

    private boolean isGitRepository(Path filePath) {
        File file = filePath.toFile();
        return file.isDirectory() && new File(file, ".git").exists();
    }
}
