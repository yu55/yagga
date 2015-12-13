package org.yu55.yagga.handler.git.command.version;

import static java.lang.Integer.parseInt;

import org.yu55.yagga.handler.generic.command.CommandOutput;

/**
 * Responsible for creating a git version value from git version command output.
 */
public class GitVersionValueFactory {

    /**
     * Interprets a command output and produces the corresponding git version value.
     *
     * @param commandOutput command output to interpret
     * @return git version value
     */
    public static int factorizeGitVersionValue(CommandOutput commandOutput) {
        return parseInt(
                commandOutput
                        .getOutputLines()
                        .get(0)
                        .getLine()
                        .split(" ")[2]
                        .replaceAll("\\.", "")
        );
    }
}
