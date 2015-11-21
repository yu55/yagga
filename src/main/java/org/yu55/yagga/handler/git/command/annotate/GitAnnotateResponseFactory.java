package org.yu55.yagga.handler.git.command.annotate;

import java.util.List;

import org.yu55.yagga.core.annotate.model.AnnotateResponse;
import org.yu55.yagga.handler.generic.command.CommandOutput;
import org.yu55.yagga.handler.generic.command.CommandOutputLine;

public class GitAnnotateResponseFactory {

    public static AnnotateResponse factorizeAnnotateResponse(CommandOutput output) {
        List<CommandOutputLine> outputLines = output.getOutputLines();

        StringBuilder annotationsStringBuilder = new StringBuilder();
        StringBuilder fileContentStringBuilder = new StringBuilder();

        for (CommandOutputLine outputLine : outputLines) {
            String annotatedLine = outputLine.getLine();
            if (output.getExitValue() == 0) {
                generateSuccessfulContent(annotationsStringBuilder, fileContentStringBuilder, annotatedLine);
            } else {
                generateFailureContent(annotationsStringBuilder, annotatedLine);
            }
        }

        return new AnnotateResponse(annotationsStringBuilder.toString(), fileContentStringBuilder.toString());
    }

    private static void generateSuccessfulContent(StringBuilder annotationsStringBuilder,
                                                  StringBuilder fileContentStringBuilder, String annotatedLine) {
        int splitIndex = annotatedLine.indexOf(")");
        annotationsStringBuilder.append(annotatedLine.substring(0, splitIndex + 1)).append("\n");
        fileContentStringBuilder.append(annotatedLine.substring(splitIndex + 1)).append("\n");
    }

    private static void generateFailureContent(StringBuilder annotationsStringBuilder, String annotatedLine) {
        annotationsStringBuilder.append(annotatedLine).append("\n");
    }

}
