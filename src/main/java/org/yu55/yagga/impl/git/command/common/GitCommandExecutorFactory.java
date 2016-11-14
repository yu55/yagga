package org.yu55.yagga.impl.git.command.common;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.common.repository.Repository;
import org.yu55.yagga.common.repository.annotate.AnnotateParameters;
import org.yu55.yagga.common.repository.grep.GrepParameters;
import org.yu55.yagga.common.command.CommandExecutor;
import org.yu55.yagga.common.command.CommandExecutorFactory;
import org.yu55.yagga.impl.git.command.annotate.GitAnnotateCommand;
import org.yu55.yagga.impl.git.command.clone.GitCloneCommand;
import org.yu55.yagga.impl.git.command.grep.GitGrepCommand;
import org.yu55.yagga.impl.git.command.refresh.GitFetchCommand;
import org.yu55.yagga.impl.git.command.refresh.GitResetCommand;
import org.yu55.yagga.impl.git.command.version.GitVersionCommand;

@Component
public class GitCommandExecutorFactory {

    private CommandExecutorFactory commandExecutorFactory;

    @Autowired
    public GitCommandExecutorFactory(CommandExecutorFactory commandExecutorFactory) {
        this.commandExecutorFactory = commandExecutorFactory;
    }

    public CommandExecutor factorizeVersion() {
        return commandExecutorFactory.factorizeGlobalCommandExecutor(new GitVersionCommand());
    }

    public CommandExecutor factorizeFetch(Repository repository) {
        return commandExecutorFactory.factorizeRepositoryCommandExecutor(repository, new GitFetchCommand());
    }

    public CommandExecutor factorizeReset(Repository repository) {
        return commandExecutorFactory.factorizeRepositoryCommandExecutor(repository, new GitResetCommand());
    }

    public CommandExecutor factorizeAnnotate(Repository repository, AnnotateParameters annotateParameters) {
        return commandExecutorFactory.factorizeRepositoryCommandExecutor(repository,
                new GitAnnotateCommand(annotateParameters));
    }

    public CommandExecutor factorizeGrep(Repository repository,
                                         GrepParameters grepParameters) {
        return commandExecutorFactory.factorizeRepositoryCommandExecutor(repository, new GitGrepCommand(grepParameters));
    }

    public CommandExecutor factorizeClone(File workingDirectory, String repositoryUrl) {
        return commandExecutorFactory.factorizeDirectoryCommandExecutor(workingDirectory,
                new GitCloneCommand(repositoryUrl));
    }
}
