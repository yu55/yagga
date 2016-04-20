package org.yu55.yagga.impl.mercurial.command.annotate;

import static org.yu55.yagga.impl.mercurial.command.annotate.MercurialAnnotateResponseFactory.factorizeAnnotateResponse;

import org.junit.Test;
import org.yu55.yagga.api.annotate.model.AnnotateResponse;
import org.yu55.yagga.api.annotate.model.AnnotateResponseLineAssert;
import org.yu55.yagga.common.command.CommandOutput;
import org.yu55.yagga.common.command.CommandOutputLine;

public class MercurialAnnotateResponseFactoryTest {

    @Test
    public void testFactorizeAnnotateResponse() throws Exception {
        // given
        CommandOutput annotateCommandOutput = new CommandOutput();
        annotateCommandOutput.addOutputLine(new CommandOutputLine(
                "sawickil 699243681ab8 Wed Jan 20 21:58:42 2016 +0100:1: Hello world!"
        ));

        // when
        AnnotateResponse annotateResponse = factorizeAnnotateResponse(annotateCommandOutput);

        // then
        AnnotateResponseLineAssert.assertThat(annotateResponse.getAnnotationResponseLines().get(0))
                .hasCommitId("699243681ab8")
                .hasAuthor("sawickil")
                .hasCommitDate("Wed Jan 20 21:58:42 2016 +0100")
                .hasLineNumber(1)
                .hasLine("Hello world!");
    }
}