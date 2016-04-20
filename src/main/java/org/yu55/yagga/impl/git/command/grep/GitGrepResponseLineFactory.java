package org.yu55.yagga.impl.git.command.grep;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import org.yu55.yagga.api.grep.model.GrepResponseLine;
import org.yu55.yagga.common.command.CommandOutput;

public class GitGrepResponseLineFactory {

    public static List<GrepResponseLine> factorizeGrepResponseLinesList(CommandOutput commandOutput) {
        if (commandOutput.getExitValue() == 0) {
            return commandOutput.getOutputLines().stream().map(
                    outputLine ->
                            factorizeGrepResponseLine(commandOutput.getRepositoryName(),
                                    outputLine.getLine())).collect(toList());
        } else {
            return Collections.emptyList();
        }
    }

    public static GrepResponseLine factorizeGrepResponseLine(String repository, String grepOutputLine) {
        StringTokenizer stringTokenizer = new StringTokenizer(grepOutputLine, ":");
        String file = stringTokenizer.nextToken();
        Integer lineNumber = null;
        try {
            lineNumber = stringTokenizer.hasMoreElements() ? Integer.parseInt(stringTokenizer.nextToken()) : null;
        } catch (NumberFormatException e) {
        }
        String line = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken() : "";
        return new GrepResponseLine(repository, file, lineNumber, line);
    }
}
