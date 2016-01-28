package org.yu55.yagga.handler.git.command.refresh;

import org.apache.commons.exec.CommandLine;
import org.yu55.yagga.handler.generic.command.CommandLineBuilder;
import org.yu55.yagga.handler.git.command.common.GitCommand;

public class GitFetchCommand implements GitCommand {

    @Override
    public CommandLine getCommandLine() {
        CommandLine commandLine = new CommandLineBuilder(COMMAND)
                .withArgument("fetch")
                .build();
        return commandLine;
    }
}
