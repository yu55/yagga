package com.github.yu55.gog;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@RestController()
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    private Repositories repositories;

    @Autowired
    public Controller(Repositories repositories) {
        this.repositories = repositories;
    }

    @RequestMapping(value = "/grep", method = RequestMethod.POST)
    public List<String> grep(@RequestBody String wanted) {
        return getStrings(wanted);
    }

    private List<String> getStrings(String wanted) {
        CommandLine commandLine = CommandLine.parse("git grep -n " + wanted);
        DefaultExecutor executor = new DefaultExecutor();
        List<File> directories = repositories.getDirectories();
        List<String> allFindings = new LinkedList<>();
        List<String> findings = new LinkedList<>();
        executor.setStreamHandler(new PumpStreamHandler(new LogOutputStream() {
            @Override
            protected void processLine(String line, int logLevel) {
                findings.add(line);
            }
        }));
        for (File directory : directories) {
            executor.setWorkingDirectory(directory);
            try {
                executor.execute(commandLine);
                allFindings.addAll(findings);
            } catch (IOException e) {
                logger.warn("Problem while searching '{}' repository: {}", directory.getAbsolutePath(), e.getMessage());
            }
            findings.clear();
        }
        return allFindings;
    }

}
