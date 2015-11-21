package org.yu55.yagga.handler.generic.command;

import java.util.function.Predicate;

import org.apache.commons.exec.CommandLine;

public class CommandLineBuilder {

    private final CommandLine commandLine;

    public CommandLineBuilder(String command) {
        this.commandLine = new CommandLine(command);
    }

    public CommandLineBuilder withArgument(String argument) {
        commandLine.addArgument(argument, false);
        return this;
    }

    public <T> CommandLineBuilder withArgument(T options, Predicate<T> predicate, String argument) {
        if (predicate.test(options)) {
            commandLine.addArgument(argument, false);
        }
        return this;
    }

    public CommandLine build() {
        return commandLine;
    }
}
