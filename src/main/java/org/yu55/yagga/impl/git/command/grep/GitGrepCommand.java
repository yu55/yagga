package org.yu55.yagga.impl.git.command.grep;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.apache.commons.exec.CommandLine;
import org.yu55.yagga.common.repository.GrepParameters;
import org.yu55.yagga.common.command.CommandLineBuilder;
import org.yu55.yagga.impl.git.command.common.GitCommand;

public class GitGrepCommand implements GitCommand {

    public static final String COMMAND_GREP = "grep";

    private final GrepParameters grepParameters;

    public GitGrepCommand(GrepParameters grepParameters) {
        this.grepParameters = grepParameters;
    }

    @Override
    public CommandLine getCommandLine() {
        CommandLine commandLine = new CommandLineBuilder(COMMAND)
                .withArgument(COMMAND_GREP)
                .withArgument("-n")
                .withArgument("-I")
                .withArgument(() -> grepParameters.isIgnoreCase(), "-i")
                .withArgument(() -> grepParameters.isOnlyFilename(), "-l")
                .withArgument(escapeSearchPhraseArgument(grepParameters.getWanted()))
                .withArgument(() -> isNotBlank(grepParameters.getFileFilter()), grepParameters.getFileFilter())
                .build();

        return commandLine;
    }

    private String escapeSearchPhraseArgument(String wanted) {
        if (wanted.startsWith("-") || wanted.startsWith(" ") || wanted.startsWith("*")) {
            return "\\" + wanted;
        } else {
            return wanted;
        }
    }
}
