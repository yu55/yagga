package org.yu55.yagga.impl.git.command.version;

import static java.lang.Integer.parseInt;
import static java.util.regex.Pattern.compile;

import java.util.List;
import java.util.regex.Matcher;

import org.yu55.yagga.common.command.CommandOutput;
import org.yu55.yagga.common.command.CommandOutputLine;

/**
 * Responsible for creating a git version value from git version command output.
 */
public class GitVersionValueFactory {

    private static final String GIT_VERSION_PATTERN = "git version (\\d\\.\\d\\.\\d)";

    /**
     * Interprets a command output and produces the corresponding git version value.
     *
     * @param commandOutput command output to interpret
     * @return git version value
     */
    public static int factorizeGitVersionValue(CommandOutput commandOutput) {
        List<CommandOutputLine> outputLines = commandOutput.getOutputLines();
        if (!outputLines.isEmpty()) {
            Matcher matcher = compile(GIT_VERSION_PATTERN).matcher(outputLines.get(0).getLine());
            if (matcher.matches()) {
                return parseInt(matcher.group(1).replaceAll("\\.", ""));
            }
        }
        throw new IllegalArgumentException("Cannot parse git version.");
    }
}
