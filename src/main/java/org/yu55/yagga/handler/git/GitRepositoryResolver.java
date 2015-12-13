package org.yu55.yagga.handler.git;

import java.nio.file.Files;
import java.nio.file.Path;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.handler.api.DvcsRepository;
import org.yu55.yagga.handler.api.DvcsRepositoryResolver;
import org.yu55.yagga.handler.generic.command.CommandOutput;
import org.yu55.yagga.handler.git.command.common.GitCommandExecutorFactory;
import org.yu55.yagga.handler.git.command.version.GitVersionValueFactory;

@Component
public class GitRepositoryResolver implements DvcsRepositoryResolver {

    public static final String GIT_REPOSITORY_DISCRIMINATOR = ".git";

    private static final Logger logger = LoggerFactory.getLogger(GitRepositoryResolver.class);

    private GitCommandExecutorFactory gitCommandExecutorFactory;

    private boolean dvcsAvailable;

    @Autowired
    public GitRepositoryResolver(GitCommandExecutorFactory gitCommandExecutorFactory) {
        this.gitCommandExecutorFactory = gitCommandExecutorFactory;
        this.dvcsAvailable = false;
    }

    @PostConstruct
    private void setDvcsAvailable() {
        CommandOutput output = gitCommandExecutorFactory.factorizeVersion().execute();
        if (output.getExitValue() == 0) {
            int version = GitVersionValueFactory.factorizeGitVersionValue(output);
            dvcsAvailable = (version >= 250);
            if (!dvcsAvailable) {
                logger.warn("Git not supported (requires version >= 2.5.0)");
            }
        }
    }

    @Override
    public boolean isDvcsSupported() {
        return dvcsAvailable;
    }

    @Override
    public boolean isRepository(Path directory) {
        return Files.isDirectory(directory.resolve(GIT_REPOSITORY_DISCRIMINATOR));
    }

    @Override
    public DvcsRepository resolveRepository(Path directory) {
        return new GitRepository(directory, gitCommandExecutorFactory);
    }

}
