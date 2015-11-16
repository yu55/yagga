package org.yu55.yagga.handler.git.command.common;

import org.apache.commons.exec.LogOutputStream;

/**
 * Simple executor stream handler producing {@link GitCommandOutput}.
 */
public class GitCommandExecutorStreamHandler extends LogOutputStream {

    private GitCommandOutput output;

    GitCommandExecutorStreamHandler(String dirName) {
        output = new GitCommandOutput(dirName);
    }

    @Override
    protected void processLine(String line, int logLevel) {
        output.addOutputLine(new GitCommandOutputLine(line));
    }

    GitCommandOutput getOutput() {
        return output;
    }
}
