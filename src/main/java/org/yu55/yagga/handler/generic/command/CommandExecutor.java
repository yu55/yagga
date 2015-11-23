package org.yu55.yagga.handler.generic.command;

import java.io.IOException;
import java.util.function.Supplier;

import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.Executor;

public class CommandExecutor {

    private final Command command;

    private final Executor executor;

    private final Supplier<CommandOutput> commandOutputSupplier;

    public CommandExecutor(Command command, Executor executor,
                           Supplier<CommandOutput> commandOutputSupplier) {
        this.command = command;
        this.executor = executor;
        this.commandOutputSupplier = commandOutputSupplier;
    }

    public CommandOutput execute() {
        String repositoryName = executor.getWorkingDirectory().getName();
        try {
            executor.execute(command.getCommandLine());
        } catch (ExecuteException execException) {
            return createExceptionalCommandOutput(repositoryName, commandOutputSupplier.get(),
                    execException.getExitValue());
        } catch (IOException e) {
            return createExceptionalCommandOutput(repositoryName, commandOutputSupplier.get(), -127);
        }
        return commandOutputSupplier.get();
    }

    private CommandOutput createExceptionalCommandOutput(
            String repositoryName, CommandOutput output, int exitValue) {
        CommandOutput exceptionCommandOutput = new CommandOutput(repositoryName, exitValue);
        exceptionCommandOutput.addOutputLine(new CommandOutputLine("Command execution failed:"));
        exceptionCommandOutput.mergeWithOutput(output);
        return exceptionCommandOutput;
    }

}
