package org.yu55.yagga.handler.git.command.pull;

import org.apache.commons.exec.CommandLine;
import org.yu55.yagga.handler.git.command.common.GitCommand;

public class GitPullCommand implements GitCommand {

    @Override
    public CommandLine getCommandLine() {
        CommandLine commandLine = CommandLine.parse(COMMAND);
        commandLine.addArgument("pull", false);
        return commandLine;
    }
}
