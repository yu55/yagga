package com.github.yu55.gog.handler.git.command;

import org.apache.commons.exec.CommandLine;

public class GitPullCommand implements GitCommand {

    @Override
    public CommandLine getCommandLine() {
        CommandLine commandLine = CommandLine.parse(COMMAND);
        commandLine.addArgument("pull", false);
        return commandLine;
    }
}
