package org.yu55.yagga.handler.mercurial;

import static org.yu55.yagga.handler.mercurial.command.annotate.MercurialAnnotateResponseFactory.factorizeAnnotateResponse;
import static org.yu55.yagga.handler.mercurial.command.grep.MercurialGrepResponseLineFactory.factorizeGrepResponseLinesList;

import java.io.File;
import java.util.List;

import org.yu55.yagga.core.annotate.model.AnnotateResponse;
import org.yu55.yagga.core.grep.model.GrepResponseLine;
import org.yu55.yagga.handler.api.DvcsRepository;
import org.yu55.yagga.handler.api.command.annotate.AnnotateParameters;
import org.yu55.yagga.handler.api.command.grep.GrepParameters;
import org.yu55.yagga.handler.generic.command.CommandOutput;
import org.yu55.yagga.handler.mercurial.command.common.MercurialCommandExecutorFactory;

public class MercurialRepository implements DvcsRepository {

    private final File directory;

    private final MercurialCommandExecutorFactory commandExecutorFactory;

    public MercurialRepository(File directory,
                               MercurialCommandExecutorFactory commandExecutorFactory) {

        if (directory == null) {
            throw new IllegalArgumentException("Directory cannot be null.");
        }
        this.directory = directory;
        this.commandExecutorFactory = commandExecutorFactory;
    }

    /**
     * @return the directory as an instance of {@link File} for this mercurial repository
     */
    public File getDirectory() {
        return directory;
    }

    /**
     * @return the name of this mercurial repository
     */
    public String getDirectoryName() {
        return directory.getName();
    }

    public boolean isDirectoryNameEqual(String repository) {
        return getDirectory().getName().equals(repository);
    }

    public void pull() {
        commandExecutorFactory.factorizePull(this).execute();
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

    /**
     * Checks if a directory is a mercurial repository
     *
     * @param directory the directory to be checked
     * @return <code>true</code> if the directory is a mercurial repository
     */
    public static boolean isMercurialRepository(File directory) {
        return directory.isDirectory() && new File(directory, ".hg").exists();
    }
}
