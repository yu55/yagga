package org.yu55.yagga.impl.mercurial;

import static org.yu55.yagga.impl.mercurial.command.annotate.MercurialAnnotateResponseFactory
        .factorizeAnnotateResponse;
import static org.yu55.yagga.impl.mercurial.command.grep.MercurialGrepResponseLineFactory
        .factorizeGrepResponseLinesList;

import java.nio.file.Path;
import java.util.List;

import org.yu55.yagga.common.model.annotate.AnnotateResponse;
import org.yu55.yagga.common.model.grep.GrepResponseLine;
import org.yu55.yagga.common.repository.Repository;
import org.yu55.yagga.common.repository.AnnotateParameters;
import org.yu55.yagga.common.repository.GrepParameters;
import org.yu55.yagga.common.command.CommandOutput;
import org.yu55.yagga.impl.mercurial.command.common.MercurialCommandExecutorFactory;

public class MercurialRepository implements Repository {

    private final Path directory;

    private final MercurialCommandExecutorFactory commandExecutorFactory;

    public MercurialRepository(Path directory, MercurialCommandExecutorFactory commandExecutorFactory) {
        if (directory == null) {
            throw new IllegalArgumentException("Directory cannot be null.");
        }
        this.directory = directory;
        this.commandExecutorFactory = commandExecutorFactory;
    }

    /**
     * @return the directory as an instance of {@link Path} for this mercurial repository
     */
    public Path getDirectory() {
        return directory;
    }

    public void refresh() {
        commandExecutorFactory.factorizeRefresh(this).execute();
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
