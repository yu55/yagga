package org.yu55.yagga.handler.git.command.common;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;

public class GitCommandExecutor {

    private final GitCommand command;

    private final Executor executor;

    public GitCommandExecutor(GitCommand command) {
        this.command = command;
        this.executor = new DefaultExecutor();
    }

    public GitCommandExecutor(GitCommand command, Executor executor) {
        this.command = command;
        this.executor = executor;
    }

    public GitCommandOutput execute(File dir) {
        executor.setWorkingDirectory(dir);
        ExecutorStreamHandler executorStreamHandler = new ExecutorStreamHandler();
        executor.setStreamHandler(new PumpStreamHandler(executorStreamHandler));
        // TODO: this whole exception handling is just a poor draft
        try {
            executor.execute(command.getCommandLine());
        } catch (ExecuteException execException) {
                return createExceptionalGitCommandOutput(executorStreamHandler, execException.getExitValue());
        } catch (IOException e) {
            return createExceptionalGitCommandOutput(executorStreamHandler, -127);
        }
        return executorStreamHandler.getOutput();
    }

    private GitCommandOutput createExceptionalGitCommandOutput(
            ExecutorStreamHandler executorStreamHandler, int exitValue) {
        GitCommandOutput exceptionGitCommandOutput = new GitCommandOutput(exitValue);
        exceptionGitCommandOutput.addOutputLine(new GitCommandOutputLine("Command execution failed:"));
        exceptionGitCommandOutput.mergeWithOutput(executorStreamHandler.getOutput());
        return exceptionGitCommandOutput;
    }

    private class ExecutorStreamHandler extends LogOutputStream {

        private GitCommandOutput output = new GitCommandOutput();

        @Override
        protected void processLine(String line, int logLevel) {
            output.addOutputLine(new GitCommandOutputLine(line));
        }

        public GitCommandOutput getOutput() {
            return output;
        }
    }

}
