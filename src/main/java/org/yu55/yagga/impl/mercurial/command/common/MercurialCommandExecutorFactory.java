package org.yu55.yagga.impl.mercurial.command.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.common.repository.Repository;
import org.yu55.yagga.common.repository.AnnotateParameters;
import org.yu55.yagga.common.repository.GrepParameters;
import org.yu55.yagga.common.command.CommandExecutor;
import org.yu55.yagga.common.command.CommandExecutorFactory;
import org.yu55.yagga.impl.mercurial.command.annotate.MercurialAnnotateCommand;
import org.yu55.yagga.impl.mercurial.command.grep.MercurialGrepCommand;
import org.yu55.yagga.impl.mercurial.command.refresh.MercurialRefreshCommand;

@Component
public class MercurialCommandExecutorFactory {

    private CommandExecutorFactory commandExecutorFactory;

    @Autowired
    public MercurialCommandExecutorFactory(CommandExecutorFactory commandExecutorFactory) {
        this.commandExecutorFactory = commandExecutorFactory;
    }

    public CommandExecutor factorizeRefresh(Repository repository) {
        return commandExecutorFactory.factorizeRepositoryCommandExecutor(repository, new MercurialRefreshCommand());
    }

    public CommandExecutor factorizeAnnotate(Repository repository, AnnotateParameters annotateParameters) {
        return commandExecutorFactory.factorizeRepositoryCommandExecutor(repository,
                new MercurialAnnotateCommand(annotateParameters));
    }

    public CommandExecutor factorizeGrep(Repository repository,
                                         GrepParameters grepParameters) {
        return commandExecutorFactory.factorizeRepositoryCommandExecutor(repository,
                new MercurialGrepCommand(grepParameters));
    }
}
