package org.yu55.yagga.handler.git.command.common;

import java.util.LinkedList;
import java.util.List;

public class GitCommandOutput {

    private List<GitCommandOutputLine> outputLines;

    public GitCommandOutput() {
        this.outputLines = new LinkedList<>();
    }

    public void addOutputLine(GitCommandOutputLine line) {
        outputLines.add(line);
    }

    public List<GitCommandOutputLine> getOutputLines() {
        return outputLines;
    }
}
