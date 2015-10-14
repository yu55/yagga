package com.github.yu55.gog.handler.git.command;

import org.apache.commons.exec.CommandLine;

public interface GitCommand {

    String COMMAND = "git";

    CommandLine getCommandLine();
}
