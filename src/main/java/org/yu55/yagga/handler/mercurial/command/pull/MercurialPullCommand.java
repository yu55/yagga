package org.yu55.yagga.handler.mercurial.command.pull;

import org.apache.commons.exec.CommandLine;
import org.yu55.yagga.handler.mercurial.command.common.MercurialCommand;

public class MercurialPullCommand implements MercurialCommand {

    @Override
    public CommandLine getCommandLine() {
        CommandLine commandLine = CommandLine.parse(COMMAND);
        commandLine.addArgument("pull", false);
        commandLine.addArgument("-u", false);
        return commandLine;
    }
}
