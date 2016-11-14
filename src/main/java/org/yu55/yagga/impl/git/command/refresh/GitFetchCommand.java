package org.yu55.yagga.impl.git.command.refresh;

import org.apache.commons.exec.CommandLine;
import org.yu55.yagga.common.command.CommandLineBuilder;
import org.yu55.yagga.impl.git.command.common.GitCommand;

public class GitFetchCommand implements GitCommand {

    @Override
    public CommandLine getCommandLine() {
        CommandLine commandLine = new CommandLineBuilder(COMMAND)
                .withArgument("fetch")
                .build();
        return commandLine;
    }
}
