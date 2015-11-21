package org.yu55.yagga.handler.git;

import static org.yu55.yagga.handler.git.command.annotate.GitAnnotateResponseFactory.factorizeAnnotateResponse;
import static org.yu55.yagga.handler.git.command.grep.GitGrepResponseLineFactory.factorizeGrepResponseLinesList;

import java.io.File;
import java.util.List;


import org.yu55.yagga.core.annotate.model.AnnotateResponse;
import org.yu55.yagga.core.grep.model.GrepResponseLine;
import org.yu55.yagga.handler.api.DvcsRepository;
import org.yu55.yagga.handler.api.command.annotate.AnnotateParameters;
import org.yu55.yagga.handler.api.command.grep.GrepParameters;
import org.yu55.yagga.handler.git.command.common.GitCommandExecutorFactory;
import org.yu55.yagga.handler.git.command.common.GitCommandOutput;

/**
 * This class represents a git repository in a file system.
 */
public class GitRepository implements DvcsRepository {

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
    public String getDirectoryName() {
        return directory.getName();
    }

    public boolean isDirectoryNameEqual(String repository) {
        return getDirectory().getName().equals(repository);
    }

    public void pull() {
        gitCommandExecutorFactory.factorizePull(this).execute();
    }

    @Override
    public AnnotateResponse annotate(AnnotateParameters annotateParameters) {
        GitCommandOutput gitCommandOutput = gitCommandExecutorFactory.factorizeAnnotate(this, annotateParameters).execute();

        // perhaps this should be command-dependent
        return factorizeAnnotateResponse(gitCommandOutput);
    }

    @Override
    public List<GrepResponseLine> grep(GrepParameters grepParameters) {
        GitCommandOutput gitCommandOutput = gitCommandExecutorFactory.factorizeGrep(this, grepParameters).execute();

        // perhaps this should be command-dependent
        return factorizeGrepResponseLinesList(gitCommandOutput);
    }
}
