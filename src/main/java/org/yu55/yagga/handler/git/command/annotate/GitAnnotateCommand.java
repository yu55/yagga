package org.yu55.yagga.handler.git.command.annotate;

import org.apache.commons.exec.CommandLine;

import org.yu55.yagga.handler.git.command.common.GitCommand;

public class GitAnnotateCommand implements GitCommand {

    private String file;

    public GitAnnotateCommand(String file) {
        this.file = file;
    }

    @Override
    public CommandLine getCommandLine() {
        CommandLine commandLine = CommandLine.parse(COMMAND);
        commandLine.addArgument("annotate", false);
        commandLine.addArgument(file, false);
        return commandLine;
    }
}
