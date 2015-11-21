package org.yu55.yagga.handler.git.command.annotate;

import org.apache.commons.exec.CommandLine;

import org.yu55.yagga.handler.api.command.annotate.AnnotateParameters;
import org.yu55.yagga.handler.git.command.common.GitCommand;

public class GitAnnotateCommand implements GitCommand {

    private AnnotateParameters annotateParameters;

    public GitAnnotateCommand(AnnotateParameters annotateParameters) {
        this.annotateParameters = annotateParameters;
    }

    @Override
    public CommandLine getCommandLine() {
        CommandLine commandLine = CommandLine.parse(COMMAND);
        commandLine.addArgument("annotate", false);
        commandLine.addArgument(annotateParameters.getFile(), false);
        return commandLine;
    }
}
