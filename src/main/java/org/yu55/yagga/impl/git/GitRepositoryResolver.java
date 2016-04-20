package org.yu55.yagga.impl.git;

import static org.yu55.yagga.impl.git.command.version.GitVersionValueFactory.factorizeGitVersionValue;

import java.nio.file.Files;
import java.nio.file.Path;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.common.repository.Repository;
import org.yu55.yagga.common.repository.RepositoryResolver;
import org.yu55.yagga.common.command.CommandOutput;
import org.yu55.yagga.impl.git.command.common.GitCommandExecutorFactory;

@Component
public class GitRepositoryResolver implements RepositoryResolver {

    public static final String GIT_REPOSITORY_DISCRIMINATOR = ".git";

    private static final Logger logger = LoggerFactory.getLogger(GitRepositoryResolver.class);

    public static final int GIT_MIN_VERSION_SUPPORTED = 250;

    private GitCommandExecutorFactory gitCommandExecutorFactory;

    private boolean supported;

    @Autowired
    public GitRepositoryResolver(GitCommandExecutorFactory gitCommandExecutorFactory) {
        this.gitCommandExecutorFactory = gitCommandExecutorFactory;
        this.supported = false;
    }

    @PostConstruct
    void checkAvailability() {
        CommandOutput output = gitCommandExecutorFactory.factorizeVersion().execute();
        if (output.getExitValue() == 0) {
            int version = factorizeGitVersionValue(output);
            supported = (version >= GIT_MIN_VERSION_SUPPORTED);
            if (!supported) {
                logger.warn("Git not supported (requires version >= 2.5.0)");
            }
        }
    }

    @Override
    public boolean isSupported() {
        return supported;
    }

    @Override
    public boolean isRepository(Path directory) {
        return Files.isDirectory(directory.resolve(GIT_REPOSITORY_DISCRIMINATOR));
    }

    @Override
    public Repository resolveRepository(Path directory) {
        return new GitRepository(directory, gitCommandExecutorFactory);
    }

}
