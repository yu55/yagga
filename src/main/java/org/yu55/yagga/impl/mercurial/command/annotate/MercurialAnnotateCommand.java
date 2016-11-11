package org.yu55.yagga.impl.mercurial.command.annotate;

import org.apache.commons.exec.CommandLine;
import org.yu55.yagga.common.repository.annotate.AnnotateParameters;
import org.yu55.yagga.impl.mercurial.command.common.MercurialCommand;

public class MercurialAnnotateCommand implements MercurialCommand {

    private final AnnotateParameters annotateParameters;

    public MercurialAnnotateCommand(AnnotateParameters annotateParameters) {
        this.annotateParameters = annotateParameters;
    }

    @Override
    public CommandLine getCommandLine() {
        CommandLine commandLine = CommandLine.parse(COMMAND);
        commandLine.addArgument("annotate", false);
        commandLine.addArgument("-u", false);
        commandLine.addArgument("-c", false);
        commandLine.addArgument("-l", false);
        commandLine.addArgument("-d", false);
        commandLine.addArgument(annotateParameters.getFile(), false);
        return commandLine;
    }
}
