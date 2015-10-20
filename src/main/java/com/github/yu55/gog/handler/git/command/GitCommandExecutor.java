package com.github.yu55.gog.handler.git.command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;

public class GitCommandExecutor {

    private final GitCommand command;

    public GitCommandExecutor(GitCommand command) {
        this.command = command;
    }

    public List<String> execute(File dir) {
        DefaultExecutor executor = new DefaultExecutor();
        executor.setWorkingDirectory(dir);
        ExecutorStreamHandler executorStreamHandler = new ExecutorStreamHandler();
        executor.setStreamHandler(new PumpStreamHandler(executorStreamHandler));
        try {
            executor.execute(command.getCommandLine());
            return executorStreamHandler.getFindings();
        } catch (IOException e) {
        }
        return new ArrayList<>();
    }

    private class ExecutorStreamHandler extends LogOutputStream {

        private List<String> findings = new LinkedList<>();

        @Override
        protected void processLine(String line, int logLevel) {
            findings.add(line);
        }

        public List<String> getFindings() {
            return findings;
        }
    }

}
