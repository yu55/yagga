package org.yu55.yagga.handler.git.command.common;

import org.apache.commons.exec.CommandLine;

public interface GitCommand {

    String COMMAND = "git";

    CommandLine getCommandLine();
}
