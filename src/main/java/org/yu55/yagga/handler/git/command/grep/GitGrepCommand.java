package org.yu55.yagga.handler.git.command.grep;

import org.apache.commons.exec.CommandLine;
import org.yu55.yagga.handler.git.command.common.GitCommand;

public class GitGrepCommand implements GitCommand {

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
        CommandLine commandLine = CommandLine.parse(COMMAND);
        commandLine.addArgument("grep", false);
        commandLine.addArgument("-n", false);
        if (gitGrepCommandOptions.isIgnoreCase()) {
            commandLine.addArgument("-i");
        }
        commandLine.addArgument(escapeSearchPhraseArgument(wanted), false);
        return commandLine;
    }
}
