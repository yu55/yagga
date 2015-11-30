package org.yu55.yagga.handler.api;

import static org.yu55.yagga.handler.git.GitRepository.isGitRepository;
import static org.yu55.yagga.handler.mercurial.MercurialRepository.isMercurialRepository;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.handler.git.GitRepository;
import org.yu55.yagga.handler.git.command.common.GitCommandExecutorFactory;
import org.yu55.yagga.handler.mercurial.MercurialRepository;
import org.yu55.yagga.handler.mercurial.command.common.MercurialCommandExecutorFactory;

/**
 * Factory of {@link DvcsRepository} objects which can instantiate the following types of repositories:
 * <ul>
 * <li>git - recognized by existence of the .git directory</li>
 * <li>mercurial - recognized by existence of the .hg directory</li></li>
 * </ul>
 */
@Component
public class DvcsRepositoryFactory {

    private final GitCommandExecutorFactory gitCommandExecutorFactory;

    private final MercurialCommandExecutorFactory mercurialCommandExecutorFactory;

    @Autowired
    public DvcsRepositoryFactory(GitCommandExecutorFactory gitCommandExecutorFactory,
                                 MercurialCommandExecutorFactory mercurialCommandExecutorFactory) {

        if (gitCommandExecutorFactory == null || mercurialCommandExecutorFactory == null) {
            throw new IllegalArgumentException("No command executor factory bean passed can be null");
        }
        this.gitCommandExecutorFactory = gitCommandExecutorFactory;
        this.mercurialCommandExecutorFactory = mercurialCommandExecutorFactory;
    }

    /**
     * Creates a repository instance based on the specified directory.
     *
     * @param directory the repository directory
     * @return the created instance of repository
     * @throws IllegalArgumentException         if the directory is null
     * @throws RepositoryInstantiationException if no supported repository can be recognized
     */
    public DvcsRepository factorizeRepository(File directory) throws RepositoryInstantiationException {
        if (directory == null) {
            throw new IllegalArgumentException("Directory cannot be null");
        }
        if (isGitRepository(directory)) {
            return new GitRepository(directory, gitCommandExecutorFactory);
        }
        if (isMercurialRepository(directory)) {
            return new MercurialRepository(directory, mercurialCommandExecutorFactory);
        }
        throw new RepositoryInstantiationException(directory);
    }

    public static class RepositoryInstantiationException extends Exception {

        public RepositoryInstantiationException(File directory) {
            super(String.format("%s is not recognized as a repository", directory.getAbsolutePath()));
        }
    }
}

