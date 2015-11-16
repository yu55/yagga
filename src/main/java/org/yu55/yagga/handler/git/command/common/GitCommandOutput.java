package org.yu55.yagga.handler.git.command.common;

import java.util.LinkedList;
import java.util.List;

public class GitCommandOutput {

    private List<GitCommandOutputLine> outputLines;

    private int exitValue;

    private String repositoryName;

    public GitCommandOutput(String repositoryName) {
        this.outputLines = new LinkedList<>();
        this.exitValue = 0;
        this.repositoryName = repositoryName;
    }

    public GitCommandOutput(String repositoryName, int exitValue) {
        this(repositoryName);
        this.exitValue = exitValue;
    }

    public void addOutputLine(GitCommandOutputLine line) {
        outputLines.add(line);
    }

    public List<GitCommandOutputLine> getOutputLines() {
        return outputLines;
    }

    public void mergeWithOutput(GitCommandOutput output) {
        outputLines.addAll(output.getOutputLines());
    }

    public int getExitValue() {
        return exitValue;
    }

    public String getRepositoryName() {
        return repositoryName;
    }
}
