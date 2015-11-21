package org.yu55.yagga.handler.generic.command;

import org.apache.commons.exec.LogOutputStream;

/**
 * Simple executor stream handler producing {@link CommandOutput}.
 */
public class CommandExecutorStreamHandler extends LogOutputStream {

    private CommandOutput output;

    CommandExecutorStreamHandler(String dirName) {
        output = new CommandOutput(dirName);
    }

    @Override
    protected void processLine(String line, int logLevel) {
        output.addOutputLine(new CommandOutputLine(line));
    }

    CommandOutput getOutput() {
        return output;
    }
}
