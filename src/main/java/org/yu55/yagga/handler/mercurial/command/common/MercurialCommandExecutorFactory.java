package org.yu55.yagga.handler.mercurial.command.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.handler.api.DvcsRepository;
import org.yu55.yagga.handler.api.command.annotate.AnnotateParameters;
import org.yu55.yagga.handler.api.command.grep.GrepParameters;
import org.yu55.yagga.handler.generic.command.CommandExecutor;
import org.yu55.yagga.handler.generic.command.CommandExecutorFactory;
import org.yu55.yagga.handler.mercurial.command.annotate.MercurialAnnotateCommand;
import org.yu55.yagga.handler.mercurial.command.grep.MercurialGrepCommand;
import org.yu55.yagga.handler.mercurial.command.refresh.MercurialRefreshCommand;

@Component
public class MercurialCommandExecutorFactory {

    private CommandExecutorFactory commandExecutorFactory;

    @Autowired
    public MercurialCommandExecutorFactory(CommandExecutorFactory commandExecutorFactory) {
        this.commandExecutorFactory = commandExecutorFactory;
    }

    public CommandExecutor factorizeRefresh(DvcsRepository repository) {
        return commandExecutorFactory.factorize(repository, new MercurialRefreshCommand());
    }

    public CommandExecutor factorizeAnnotate(DvcsRepository repository, AnnotateParameters annotateParameters) {
        return commandExecutorFactory.factorize(repository, new MercurialAnnotateCommand(annotateParameters));
    }

    public CommandExecutor factorizeGrep(DvcsRepository repository,
                                         GrepParameters grepParameters) {
        return commandExecutorFactory.factorize(repository, new MercurialGrepCommand(grepParameters));
    }
}
