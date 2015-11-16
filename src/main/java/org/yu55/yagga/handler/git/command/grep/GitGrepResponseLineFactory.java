package org.yu55.yagga.handler.git.command.grep;

import static java.util.stream.Collectors.toList;
import static org.yu55.yagga.core.grep.model.GrepResponseLine.factorizeGrepResponseLine;

import java.util.Collections;
import java.util.List;

import org.yu55.yagga.core.grep.model.GrepResponseLine;
import org.yu55.yagga.handler.git.command.common.GitCommandOutput;

public class GitGrepResponseLineFactory {

    public static List<GrepResponseLine> factorizeGrepResponseLinesList(GitCommandOutput gitCommandOutput) {
        if (gitCommandOutput.getExitValue() == 0) {
            return gitCommandOutput.getOutputLines().stream().map(
                    outputLine ->
                            factorizeGrepResponseLine(gitCommandOutput.getRepositoryName(),
                                    outputLine.getLine())).collect(toList());
        } else {
            return Collections.emptyList();
        }
    }
}
