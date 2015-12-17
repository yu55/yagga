package org.yu55.yagga.handler.git;

import static org.yu55.yagga.handler.git.command.version.GitVersionValueFactory.factorizeGitVersionValue;

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

@Component
public class GitRepositoryResolver implements DvcsRepositoryResolver {

    public static final String GIT_REPOSITORY_DISCRIMINATOR = ".git";

    private static final Logger logger = LoggerFactory.getLogger(GitRepositoryResolver.class);

    public static final int GIT_MIN_VERSION_SUPPORTED = 250;

    private GitCommandExecutorFactory gitCommandExecutorFactory;

    private boolean dvcsSupported;

    @Autowired
    public GitRepositoryResolver(GitCommandExecutorFactory gitCommandExecutorFactory) {
        this.gitCommandExecutorFactory = gitCommandExecutorFactory;
        this.dvcsSupported = false;
    }

    @PostConstruct
    void checkDvcsAvailable() {
        CommandOutput output = gitCommandExecutorFactory.factorizeVersion().execute();
        if (output.getExitValue() == 0) {
            int version = factorizeGitVersionValue(output);
            dvcsSupported = (version >= GIT_MIN_VERSION_SUPPORTED);
            if (!dvcsSupported) {
                logger.warn("Git not supported (requires version >= 2.5.0)");
            }
        }
    }

    @Override
    public boolean isDvcsSupported() {
        return dvcsSupported;
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
