package org.yu55.yagga.impl.mercurial.command.grep;

import org.yu55.yagga.common.model.grep.GrepResponseLine;
import org.yu55.yagga.common.command.CommandOutput;

import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import static java.util.stream.Collectors.toList;

public class MercurialGrepResponseLineFactory {

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
        String revision = stringTokenizer.nextToken();
        int lineNumber = 0;
        try {
            lineNumber = stringTokenizer.hasMoreElements() ? Integer.parseInt(stringTokenizer.nextToken()) : 0;
        } catch (NumberFormatException e) {
        }
        String line = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken() : "";
        return new GrepResponseLine(repository, file, lineNumber, line);
    }
}
