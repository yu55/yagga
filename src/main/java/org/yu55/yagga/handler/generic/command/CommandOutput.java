package org.yu55.yagga.handler.generic.command;

import java.util.LinkedList;
import java.util.List;

public class CommandOutput {

    private List<CommandOutputLine> outputLines;

    private int exitValue;

    private String repositoryName;

    public CommandOutput(String repositoryName) {
        this.outputLines = new LinkedList<>();
        this.exitValue = 0;
        this.repositoryName = repositoryName;
    }

    public CommandOutput(String repositoryName, int exitValue) {
        this(repositoryName);
        this.exitValue = exitValue;
    }

    public void addOutputLine(CommandOutputLine line) {
        outputLines.add(line);
    }

    public List<CommandOutputLine> getOutputLines() {
        return outputLines;
    }

    public void mergeWithOutput(CommandOutput output) {
        outputLines.addAll(output.getOutputLines());
    }

    public int getExitValue() {
        return exitValue;
    }

    public String getRepositoryName() {
        return repositoryName;
    }
}
