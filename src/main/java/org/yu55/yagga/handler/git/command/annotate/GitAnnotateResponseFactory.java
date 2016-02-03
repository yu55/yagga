package org.yu55.yagga.handler.git.command.annotate;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yu55.yagga.core.annotate.model.AnnotateResponse;
import org.yu55.yagga.core.annotate.model.AnnotateResponseLine;
import org.yu55.yagga.handler.generic.command.CommandOutput;
import org.yu55.yagga.handler.generic.command.CommandOutputLine;

public class GitAnnotateResponseFactory {

    private static final String LINE_PATTERN =
            "(\\w+)\\s+\\(\\s?(.+)\\s+(\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}\\s+[\\+\\-]?\\d{4})\\s+(\\d+)\\)(" +
                    ".*)";

    public static AnnotateResponse factorizeAnnotateResponse(CommandOutput output) {
        List<CommandOutputLine> outputLines = output.getOutputLines();
        AnnotateResponse response = new AnnotateResponse();

        for (CommandOutputLine outputLine : outputLines) {
            String annotatedLine = outputLine.getLine();
            if (output.getExitValue() == 0) {
                Matcher matcher = Pattern.compile(LINE_PATTERN).matcher(annotatedLine);
                if (matcher.matches()) {
                    String author = matcher.group(2).trim();
                    AnnotateResponseLine annotationResponseLine = new AnnotateResponseLine(
                            !author.equals("Not Committed Yet") ? matcher.group(1).trim() : "",
                            author, matcher.group(3).trim(),
                            Integer.parseInt(matcher.group(4).trim()),
                            matcher.group(5));
                    response.addAnnotationResponseLine(annotationResponseLine);
                }
            } else {
                response.addAnnotationResponseLine(new AnnotateResponseLine(annotatedLine));
            }
        }

        return response;
    }

    private static class SuccessfulGitAnnotateResponseProducer {

    }

}
