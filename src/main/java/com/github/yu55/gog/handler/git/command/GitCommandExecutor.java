package com.github.yu55.gog.handler.git.command;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.DefaultExecutor;
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
        try {
            executor.execute(command.getCommandLine());
            return executorStreamHandler.getOutput();
        } catch (IOException e) {
        }
        return new GitCommandOutput();
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
