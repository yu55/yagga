package org.yu55.yagga.handler.git;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.yu55.yagga.core.annotate.model.AnnotateResponse;
import org.yu55.yagga.core.grep.model.GrepResponseLine;
import org.yu55.yagga.handler.git.command.common.GitCommandExecutorFactory;
import org.yu55.yagga.handler.git.command.common.GitCommandOutput;
import org.yu55.yagga.handler.git.command.common.GitCommandOutputLine;

/**
 * This class represents a git repository in a file system.
 */
public class GitRepository {

    private final File directory;

    private final GitCommandExecutorFactory gitCommandExecutorFactory;

    public GitRepository(File directory,
                         GitCommandExecutorFactory gitCommandExecutorFactory) {

        if (directory == null) {
            throw new IllegalArgumentException("Directory cannot be null.");
        }
        this.directory = directory;
        this.gitCommandExecutorFactory = gitCommandExecutorFactory;
    }

    /**
     * @return the directory as an instance of {@link File} for this git repository
     */
    public File getDirectory() {
        return directory;
    }

    /**
     * @return the name of this git repository
     */
    public String getName() {
        return directory.getName();
    }

    public void pull() {
        gitCommandExecutorFactory.factorizePull().execute(directory);
    }

    public boolean isDirectoryNameEqual(String repository) {
        return getDirectory().getName().equals(repository);
    }

    public AnnotateResponse annotate(String file) {
        GitCommandOutput gitCommandOutput = gitCommandExecutorFactory.factorizeAnnotate(file).execute(directory);

        //I'm not author of code below, it was moved from "GitAnnotateHandler"
        //TODO: Rewrite

        List<GitCommandOutputLine> outputLines = gitCommandOutput.getOutputLines();

        StringBuilder annotationsStringBuilder = new StringBuilder();
        StringBuilder fileContentStringBuilder = new StringBuilder();

        // TODO: this should be done smarter
        for (GitCommandOutputLine outputLine : outputLines) {
            String annotatedLine = outputLine.getLine();
            if (gitCommandOutput.getExitValue() == 0) {
                int splitIndex = annotatedLine.indexOf(")");
                annotationsStringBuilder.append(annotatedLine.substring(0, splitIndex + 1)).append("\n");
                fileContentStringBuilder.append(annotatedLine.substring(splitIndex + 1)).append("\n");
            } else {
                annotationsStringBuilder.append(annotatedLine).append("\n");
            }
        }

        return new AnnotateResponse(annotationsStringBuilder.toString(), fileContentStringBuilder.toString());
    }

    public List<GrepResponseLine> grep(String wanted) {
        GitCommandOutput gitCommandOutput = gitCommandExecutorFactory.factorizeGrep(wanted).execute(directory);
        if (gitCommandOutput.getExitValue() == 0) {
            return gitCommandOutput.getOutputLines().stream().map(
                    outputLine -> GrepResponseLine.fromGrepOutputLine(getName(),
                            outputLine.getLine())).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}
