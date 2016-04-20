package org.yu55.yagga.impl.git;

import static org.yu55.yagga.impl.git.command.annotate.GitAnnotateResponseFactory.factorizeAnnotateResponse;
import static org.yu55.yagga.impl.git.command.grep.GitGrepResponseLineFactory.factorizeGrepResponseLinesList;

import java.nio.file.Path;
import java.util.List;

import org.yu55.yagga.api.annotate.model.AnnotateResponse;
import org.yu55.yagga.api.grep.model.GrepResponseLine;
import org.yu55.yagga.common.repository.Repository;
import org.yu55.yagga.common.parameters.AnnotateParameters;
import org.yu55.yagga.common.parameters.GrepParameters;
import org.yu55.yagga.common.command.CommandOutput;
import org.yu55.yagga.impl.git.command.common.GitCommandExecutorFactory;

/**
 * This class represents a git repository in a file system.
 */
public class GitRepository implements Repository {

    private final Path directory;

    private final GitCommandExecutorFactory commandExecutorFactory;

    public GitRepository(Path directory, GitCommandExecutorFactory commandExecutorFactory) {
        this.directory = directory;
        this.commandExecutorFactory = commandExecutorFactory;
    }

    /**
     * @return the directory as an instance of {@link Path} for this git repository
     */
    public Path getDirectory() {
        return directory;
    }

    public void refresh() {
        commandExecutorFactory.factorizeFetch(this).execute();
        commandExecutorFactory.factorizeReset(this).execute();
    }

    @Override
    public AnnotateResponse annotate(AnnotateParameters annotateParameters) {
        CommandOutput commandOutput = commandExecutorFactory.factorizeAnnotate(this, annotateParameters).execute();

        // perhaps this should be command-dependent
        return factorizeAnnotateResponse(commandOutput);
    }

    @Override
    public List<GrepResponseLine> grep(GrepParameters grepParameters) {
        CommandOutput commandOutput = commandExecutorFactory.factorizeGrep(this, grepParameters).execute();

        // perhaps this should be command-dependent
        return factorizeGrepResponseLinesList(commandOutput);
    }
}
