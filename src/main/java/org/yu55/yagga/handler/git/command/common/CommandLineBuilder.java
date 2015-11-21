package org.yu55.yagga.handler.git.command.common;

import java.util.function.Predicate;

import org.apache.commons.exec.CommandLine;
import org.yu55.yagga.handler.git.command.grep.GitGrepCommandOptions;

public class CommandLineBuilder {

    private final CommandLine commandLine;

    private final GitGrepCommandOptions options;

    public CommandLineBuilder(String command, GitGrepCommandOptions options) {
        this.commandLine = new CommandLine(command);
        this.options = options;
    }

    public CommandLineBuilder withArgument(String argument) {
        commandLine.addArgument(argument, false);
        return this;
    }

    public CommandLineBuilder withArgument(Predicate<GitGrepCommandOptions> predicate, String argument) {
        if (predicate.test(options)) {
            commandLine.addArgument(argument, false);
        }
        return this;
    }

    public CommandLine build() {
        return commandLine;
    }
}