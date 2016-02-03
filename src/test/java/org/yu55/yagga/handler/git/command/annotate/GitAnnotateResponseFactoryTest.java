package org.yu55.yagga.handler.git.command.annotate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.yu55.yagga.handler.git.command.annotate.GitAnnotateResponseFactory.factorizeAnnotateResponse;

import org.junit.Test;
import org.yu55.yagga.core.annotate.model.AnnotateResponse;
import org.yu55.yagga.core.annotate.model.AnnotateResponseLine;
import org.yu55.yagga.handler.generic.command.CommandOutput;
import org.yu55.yagga.handler.generic.command.CommandOutputLine;

public class GitAnnotateResponseFactoryTest {

    @Test
    public void testFactorizeAnnotateResponse2() throws Exception {
        // given
        CommandOutput annotateCommandOutput = new CommandOutput();
        annotateCommandOutput.addOutputLine(new CommandOutputLine(
                "af7545c8\t( Mikołaj Fejzer    2015-11-21 20:07:59 +0100    1)package org.yu55.yagga.handler.api.command.grep;"
        ));

        // when
        AnnotateResponse annotateResponse = factorizeAnnotateResponse(annotateCommandOutput);

        // then
        // TODO: change assertions to custom ones (generated)!
        AnnotateResponseLine annotateResponseLine = annotateResponse.getAnnotationResponseLines().get(0);
        assertThat(annotateResponseLine.getCommitId()).isEqualTo("af7545c8");
        assertThat(annotateResponseLine.getAuthor()).isEqualTo("Mikołaj Fejzer");
        assertThat(annotateResponseLine.getCommitDate()).isEqualTo("2015-11-21 20:07:59 +0100");
        assertThat(annotateResponseLine.getLineNumber()).isEqualTo(1);
        assertThat(annotateResponseLine.getLine()).isEqualTo("package org.yu55.yagga.handler.api.command.grep;");
    }
}