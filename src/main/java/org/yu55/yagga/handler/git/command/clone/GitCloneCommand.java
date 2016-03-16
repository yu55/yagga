package org.yu55.yagga.handler.git.command.clone;

import org.apache.commons.exec.CommandLine;
import org.yu55.yagga.handler.generic.command.CommandLineBuilder;
import org.yu55.yagga.handler.git.command.common.GitCommand;

public class GitCloneCommand implements GitCommand {

    public static final String COMMAND_CLONE = "clone";

    private String repositoryUrl;

    public GitCloneCommand(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    @Override
    public CommandLine getCommandLine() {
        CommandLine commandLine = new CommandLineBuilder(COMMAND)
                .withArgument(COMMAND_CLONE)
                .withArgument(repositoryUrl)
                .build();
        return commandLine;
    }

}
