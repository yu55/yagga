package com.github.yu55.gog;

import org.apache.commons.exec.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

@RestController()
public class Controller {

    @RequestMapping(value = "/grep", method = RequestMethod.POST)
    public List<String> grep(@RequestBody String wanted) {
        return getStrings(wanted);
    }

    private List<String> getStrings(String wanted) {
        CommandLine commandLine = CommandLine.parse("git grep -n " + wanted);
        DefaultExecutor executor = new DefaultExecutor();
        Path dir = new File("\\repositories").toPath();
        final List<String> allLines = new LinkedList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path entry: stream) {
                if(entry.toFile().isDirectory()) {
                    final List<String> lines = new LinkedList<>();
                    executor.setWorkingDirectory(entry.toFile());

                    executor.setStreamHandler(new PumpStreamHandler(new LogOutputStream() {
                        @Override
                        protected void processLine(String line, int logLevel) {
                            lines.add(line);
                        }
                    }));

                    try {
                        executor.execute(commandLine);
                        allLines.addAll(lines);
                    } catch (ExecuteException e) {
                        System.out.println("Uuups: " + e.getMessage());
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("catch ex=" + ex.getMessage());
        }

        System.out.println("return allLines=" + allLines);
        return allLines;
    }
}
