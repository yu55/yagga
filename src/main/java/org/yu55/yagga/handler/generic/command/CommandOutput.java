package org.yu55.yagga.handler.generic.command;

import java.util.LinkedList;
import java.util.List;

public class CommandOutput {

    private List<CommandOutputLine> outputLines;

    private int exitValue;

    private String repositoryName;

    public CommandOutput(String repositoryName) {
        this();
        this.repositoryName = repositoryName;
    }

    public CommandOutput(String repositoryName, int exitValue) {
        this(repositoryName);
        this.exitValue = exitValue;
    }

    public CommandOutput() {
        this.outputLines = new LinkedList<>();
        this.exitValue = 0;
    }

    public CommandOutput addOutputLine(CommandOutputLine line) {
        outputLines.add(line);
        return this;
    }

    public List<CommandOutputLine> getOutputLines() {
        return outputLines;
    }

    public CommandOutput mergeWithOutput(CommandOutput output) {
        outputLines.addAll(output.getOutputLines());
        return this;
    }

    public int getExitValue() {
        return exitValue;
    }

    public String getRepositoryName() {
        return repositoryName;
    }
}
