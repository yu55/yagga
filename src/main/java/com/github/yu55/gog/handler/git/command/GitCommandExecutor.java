package com.github.yu55.gog.handler.git.command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.yu55.gog.handler.git.GitGrepHandler;

public class GitCommandExecutor {

    private static final Logger logger = LoggerFactory.getLogger(GitGrepHandler.class);

    private final GitCommand command;

    public GitCommandExecutor(GitCommand command) {
        this.command = command;
    }

    public List<String> execute(File dir) {
        DefaultExecutor executor = new DefaultExecutor();
        executor.setWorkingDirectory(dir);
        ExecutorStreamHandler executorStreamHandler = new ExecutorStreamHandler(dir.getName());
        executor.setStreamHandler(new PumpStreamHandler(executorStreamHandler));
        try {
            executor.execute(command.getCommandLine());
            return executorStreamHandler.getFindings();
        } catch (IOException e) {
            logger.warn("Problem while searching '{}' repository: {}", dir.getAbsolutePath(), e.getMessage());
        }
        return new ArrayList<>();
    }

    private class ExecutorStreamHandler extends LogOutputStream {

        private List<String> findings = new LinkedList<>();

        private String repositoryName;

        public ExecutorStreamHandler(String repositoryName) {
            this.repositoryName = repositoryName;
        }

        @Override
        protected void processLine(String line, int logLevel) {
            findings.add(String.format("[%s] %s", repositoryName, line));
        }

        public List<String> getFindings() {
            return findings;
        }
    }

}
