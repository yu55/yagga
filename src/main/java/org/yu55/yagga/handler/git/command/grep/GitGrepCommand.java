package org.yu55.yagga.handler.git.command.grep;

import org.apache.commons.exec.CommandLine;
import org.yu55.yagga.handler.git.command.common.CommandLineBuilder;
import org.yu55.yagga.handler.git.command.common.GitCommand;

public class GitGrepCommand implements GitCommand {

    public static final String COMMAND_GREP = "grep";

    private final String wanted;

    private final GitGrepCommandOptions gitGrepCommandOptions;

    public GitGrepCommand(String wanted, GitGrepCommandOptions gitGrepCommandOptions) {
        this.wanted = wanted;
        this.gitGrepCommandOptions = gitGrepCommandOptions;
    }

    private String escapeSearchPhraseArgument(String wanted) {
        if (wanted.startsWith("-") || wanted.startsWith(" ")) {
            return "\\" + wanted;
        } else {
            return wanted;
        }
    }

    @Override
    public CommandLine getCommandLine() {
        CommandLine commandLine = new CommandLineBuilder(COMMAND, gitGrepCommandOptions)
                .withArgument(COMMAND_GREP)
                .withArgument("-n")
                .withArgument("-I")
                .withArgument(GitGrepCommandOptions::isIgnoreCase, "-i")
                .withArgument(escapeSearchPhraseArgument(wanted))
                .build();

        return commandLine;
    }
}
