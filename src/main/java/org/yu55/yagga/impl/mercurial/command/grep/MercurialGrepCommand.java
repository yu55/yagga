package org.yu55.yagga.impl.mercurial.command.grep;

import org.apache.commons.exec.CommandLine;
import org.yu55.yagga.common.parameters.GrepParameters;
import org.yu55.yagga.common.command.CommandLineBuilder;
import org.yu55.yagga.impl.mercurial.command.common.MercurialCommand;

public class MercurialGrepCommand implements MercurialCommand {

    private final GrepParameters grepParameters;

    public MercurialGrepCommand(GrepParameters grepParameters) {
        this.grepParameters = grepParameters;
    }

    public static final String COMMAND_GREP = "grep";

    private String escapeSearchPhraseArgument(String wanted) {
        if (wanted.startsWith("-") || wanted.startsWith(" ") || wanted.startsWith("*")) {
            return "\\" + wanted;
        } else {
            return wanted;
        }
    }

    @Override
    public CommandLine getCommandLine() {
        CommandLine commandLine = new CommandLineBuilder(COMMAND)
                .withArgument(COMMAND_GREP)
                .withArgument("-n")
                .withArgument(() -> grepParameters.isIgnoreCase(), "-i")
                .withArgument(() -> grepParameters.isOnlyFilename(), "-l")
                .withArgument(escapeSearchPhraseArgument(grepParameters.getWanted()))
                .build();

        return commandLine;
    }
}
