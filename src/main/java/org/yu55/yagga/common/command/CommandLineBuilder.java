package org.yu55.yagga.common.command;

import java.util.function.BooleanSupplier;

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

    public <T> CommandLineBuilder withArgument(BooleanSupplier booleanSupplier, String argument) {
        if (booleanSupplier.getAsBoolean()) {
            commandLine.addArgument(argument, false);
        }
        return this;
    }

    public CommandLine build() {
        return commandLine;
    }
}
