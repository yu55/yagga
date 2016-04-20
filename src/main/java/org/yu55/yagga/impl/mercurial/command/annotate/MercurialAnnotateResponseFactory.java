package org.yu55.yagga.impl.mercurial.command.annotate;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yu55.yagga.api.annotate.model.AnnotateResponse;
import org.yu55.yagga.api.annotate.model.AnnotateResponseLine;
import org.yu55.yagga.common.command.CommandOutput;
import org.yu55.yagga.common.command.CommandOutputLine;

public class MercurialAnnotateResponseFactory {

    private static final String LINE_PATTERN =
            "(\\w+)\\s+(\\w{12})\\s+(\\w{3}\\s+\\w{3}\\s+\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}\\s+\\d{4}\\s+[\\+\\-]?\\d{4})" +
                    ":(\\d+):\\s+(.*)";

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
            AnnotateResponseLine annotationResponseLine = new AnnotateResponseLine(
                    matcher.group(2).trim(),
                    matcher.group(1).trim(), matcher.group(3).trim(),
                    Integer.parseInt(matcher.group(4).trim()),
                    matcher.group(5));
            response.addAnnotationResponseLine(annotationResponseLine);
        }
    }

    private static void generateUnsuccessfulContent(AnnotateResponse response, String line) {
        response.addAnnotationResponseLine(new AnnotateResponseLine(line));
    }

}
