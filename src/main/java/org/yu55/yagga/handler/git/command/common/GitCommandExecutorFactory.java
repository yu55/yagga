package org.yu55.yagga.handler.git.command.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.handler.api.DvcsRepository;
import org.yu55.yagga.handler.api.command.annotate.AnnotateParameters;
import org.yu55.yagga.handler.api.command.grep.GrepParameters;
import org.yu55.yagga.handler.generic.command.CommandExecutor;
import org.yu55.yagga.handler.generic.command.CommandExecutorFactory;
import org.yu55.yagga.handler.git.command.annotate.GitAnnotateCommand;
import org.yu55.yagga.handler.git.command.grep.GitGrepCommand;
import org.yu55.yagga.handler.git.command.pull.GitPullCommand;

@Component
public class GitCommandExecutorFactory {

    private CommandExecutorFactory commandExecutorFactory;

    @Autowired
    public GitCommandExecutorFactory(CommandExecutorFactory commandExecutorFactory) {
        this.commandExecutorFactory = commandExecutorFactory;
    }

    public CommandExecutor factorizePull(DvcsRepository repository) {
        return commandExecutorFactory.factorize(repository, new GitPullCommand());
    }

    public CommandExecutor factorizeAnnotate(DvcsRepository repository, AnnotateParameters annotateParameters) {
        return commandExecutorFactory.factorize(repository, new GitAnnotateCommand(annotateParameters));
    }

    public CommandExecutor factorizeGrep(DvcsRepository repository,
                                         GrepParameters grepParameters) {
        return commandExecutorFactory.factorize(repository, new GitGrepCommand(grepParameters));
    }
}
