package com.github.yu55.gog.handler.git.command;

import org.apache.commons.exec.CommandLine;

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
