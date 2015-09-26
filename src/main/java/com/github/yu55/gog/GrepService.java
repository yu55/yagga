package com.github.yu55.gog;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Component
public class GrepService {

    private static final Logger logger = LoggerFactory.getLogger(GrepService.class);

    private static final String COMMAND = "git grep -n ";

    private Repositories repositories;

    @Autowired
    public GrepService(Repositories repositories) {
        this.repositories = repositories;
    }

    public List<String> grep(GrepRequest wanted) {
        List<String> allFindings = new LinkedList<>();
        List<File> directories = repositories.getDirectories();

        CommandLine commandLine = CommandLine.parse(COMMAND + wanted.getWanted());
        DefaultExecutor executor = new DefaultExecutor();
        ExecutorStreamHandler executorStreamHandler = new ExecutorStreamHandler();
        executor.setStreamHandler(new PumpStreamHandler(executorStreamHandler));
        for (File directory : directories) {
            if (wanted.getRepositories().contains(directory.getName())) {
                executor.setWorkingDirectory(directory);
                try {
                    executor.execute(commandLine);
                    allFindings.addAll(executorStreamHandler.getFindings());
                } catch (IOException e) {
                    logger.warn("Problem while searching '{}' repository: {}", directory.getAbsolutePath(), e.getMessage());
                }
                executorStreamHandler.clearFindings();
            }
        }

        return allFindings;
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

        public void clearFindings() {
            findings.clear();
        }
    }
}
