package org.yu55.yagga.handler.git.command.common;

import java.io.IOException;
import java.util.function.Supplier;

import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.Executor;

public class GitCommandExecutor {

    private final GitCommand command;

    private final Executor executor;

    private final Supplier<GitCommandOutput> gitCommandOutputSupplier;

    public GitCommandExecutor(GitCommand command, Executor executor,
                              Supplier<GitCommandOutput> gitCommandOutputSupplier) {
        this.command = command;
        this.executor = executor;
        this.gitCommandOutputSupplier = gitCommandOutputSupplier;
    }

    public GitCommandOutput execute() {
        String repositoryName = executor.getWorkingDirectory().getName();
        try {
            executor.execute(command.getCommandLine());
        } catch (ExecuteException execException) {
            return createExceptionalGitCommandOutput(repositoryName, gitCommandOutputSupplier.get(),
                    execException.getExitValue());
        } catch (IOException e) {
            return createExceptionalGitCommandOutput(repositoryName, gitCommandOutputSupplier.get(), -127);
        }
        return gitCommandOutputSupplier.get();
    }

    private GitCommandOutput createExceptionalGitCommandOutput(
            String repositoryName, GitCommandOutput output, int exitValue) {
        GitCommandOutput exceptionGitCommandOutput = new GitCommandOutput(repositoryName, exitValue);
        exceptionGitCommandOutput.addOutputLine(new GitCommandOutputLine("Command execution failed:"));
        exceptionGitCommandOutput.mergeWithOutput(output);
        return exceptionGitCommandOutput;
    }

}
