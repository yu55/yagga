package org.yu55.yagga.impl.git.command.annotate;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yu55.yagga.api.annotate.model.AnnotateResponse;
import org.yu55.yagga.api.annotate.model.AnnotateResponseLine;
import org.yu55.yagga.common.command.CommandOutput;
import org.yu55.yagga.common.command.CommandOutputLine;

public class GitAnnotateResponseFactory {

    private static final String LINE_PATTERN =
            "(\\w+)\\s+\\(\\s?(.+)\\s+(\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}\\s+[\\+\\-]?\\d{4})\\s+(\\d+)\\)(" +
                    ".*)";

    /*
     * Don't touch it for now as I think we will have to re-think of the entire code structure, which does not seem
     * pretty well right now;)
     */
    public static AnnotateResponse factorizeAnnotateResponse(CommandOutput output) {
        List<CommandOutputLine> outputLines = output.getOutputLines();
        AnnotateResponse response = new AnnotateResponse();

        for (CommandOutputLine outputLine : outputLines) {
            String annotatedLine = outputLine.getLine();
            if (output.getExitValue() == 0) {
                generateSuccessfulContent(response, annotatedLine);
            } else {
                generateUnsuccessfulContent(response, annotatedLine);
            }
        }
        return response;
    }

    private static void generateSuccessfulContent(AnnotateResponse response, String annotatedLine) {
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
    }

    private static void generateUnsuccessfulContent(AnnotateResponse response, String line) {
        response.addAnnotationResponseLine(new AnnotateResponseLine(line));
    }

}
