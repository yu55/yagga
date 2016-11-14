package org.yu55.yagga.impl.git.command.annotate;

import static org.yu55.yagga.common.model.annotate.AnnotateResponseLineAssert.assertThat;
import static org.yu55.yagga.impl.git.command.annotate.GitAnnotateResponseFactory.factorizeAnnotateResponse;

import org.junit.Test;
import org.yu55.yagga.common.command.CommandOutput;
import org.yu55.yagga.common.command.CommandOutputLine;
import org.yu55.yagga.common.model.annotate.AnnotateResponse;

public class GitAnnotateResponseFactoryTest {

    @Test
    public void testFactorizeAnnotateResponse() throws Exception {
        // given
        CommandOutput annotateCommandOutput = new CommandOutput();
        annotateCommandOutput.addOutputLine(new CommandOutputLine(
                "af7545c8\t( Mikołaj    2015-11-21 20:07:59 +0100    1)package org.yu55.yagga.core.handlers.grep;"
        ));

        // when
        AnnotateResponse annotateResponse = factorizeAnnotateResponse(annotateCommandOutput);

        // then
        assertThat(annotateResponse.getAnnotationResponseLines().get(0))
                .hasCommitId("af7545c8")
                .hasAuthor("Mikołaj")
                .hasCommitDate("2015-11-21 20:07:59 +0100")
                .hasLineNumber(1)
                .hasLine("package org.yu55.yagga.core.handlers.grep;");
    }
}