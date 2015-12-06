package org.yu55.yagga.handler.git;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.handler.api.DvcsRepository;
import org.yu55.yagga.handler.api.DvcsRepositoryDescriptor;
import org.yu55.yagga.handler.git.command.common.GitCommandExecutorFactory;

@Component
public class GitRepositoryDescriptor implements DvcsRepositoryDescriptor {

    public static final String GIT_REPOSITORY_DISCRIMINATOR = ".git";

    private GitCommandExecutorFactory gitCommandExecutorFactory;

    @Autowired
    GitRepositoryDescriptor(GitCommandExecutorFactory gitCommandExecutorFactory) {
        this.gitCommandExecutorFactory = gitCommandExecutorFactory;
    }

    @Override
    public boolean isRepository(Path directory) {
        return Files.isDirectory(directory.resolve(GIT_REPOSITORY_DISCRIMINATOR));
    }

    @Override
    public DvcsRepository createRepository(Path directory) {
        return new GitRepository(directory, gitCommandExecutorFactory);
    }

}
