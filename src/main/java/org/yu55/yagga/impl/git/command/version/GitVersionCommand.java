package org.yu55.yagga.impl.git.command.version;

import org.apache.commons.exec.CommandLine;
import org.yu55.yagga.common.command.CommandLineBuilder;
import org.yu55.yagga.impl.git.command.common.GitCommand;

/**
 * Represents a git version command.
 */
public class GitVersionCommand implements GitCommand {

    private static final String COMMAND_VERSION = "--version";

    @Override
    public CommandLine getCommandLine() {
        CommandLine commandLine = new CommandLineBuilder(COMMAND)
                .withArgument(COMMAND_VERSION)
                .build();

        return commandLine;
    }
}
