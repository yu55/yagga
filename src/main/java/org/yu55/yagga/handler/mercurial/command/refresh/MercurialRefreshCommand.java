package org.yu55.yagga.handler.mercurial.command.refresh;

import org.apache.commons.exec.CommandLine;
import org.yu55.yagga.handler.mercurial.command.common.MercurialCommand;

public class MercurialRefreshCommand implements MercurialCommand {

    @Override
    public CommandLine getCommandLine() {
        CommandLine commandLine = CommandLine.parse(COMMAND);
        commandLine.addArgument("refresh", false);
        commandLine.addArgument("-u", false);
        return commandLine;
    }
}
