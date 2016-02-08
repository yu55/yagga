package org.yu55.yagga.handler.git.command.grep;

import static org.yu55.yagga.utils.assertion.CustomAssertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.yu55.yagga.core.grep.model.GrepResponseLine;
import org.yu55.yagga.handler.generic.command.CommandOutput;
import org.yu55.yagga.handler.generic.command.CommandOutputLine;

public class GitGrepResponseLineFactoryTest {

    @Test
    public void shouldReturnGrepResponseLine() throws Exception {
        // given
        String grepOutputLine =
                "src/main/java/org/yu55/yagga/core/grep/model/GrepRequest.java:13:public class GrepRequest {";
        String repositoryName = "myRepository";

        // when
        GrepResponseLine expected = GitGrepResponseLineFactory.factorizeGrepResponseLine(repositoryName,
                grepOutputLine);

        // then
        assertThat(expected)
                .hasRepository(repositoryName)
                .hasLineNumber(13)
                .hasFile("src/main/java/org/yu55/yagga/core/grep/model/GrepRequest.java")
                .hasMatchedTextLine("public class GrepRequest {");
    }

    @Test
    public void shouldReturnGrepResponseLinesList() {
        // given
        String repositoryName = "myRepository";
        CommandOutput commandOutput = new CommandOutput(repositoryName)
                .addOutputLine(new CommandOutputLine(
                        "src/main/java/org/yu55/yagga/core/grep/model/GrepRequest.java:13:public class GrepRequest {"
                ))
                .addOutputLine(new CommandOutputLine(
                        "src/main/java/org/yu55/yagga/core/grep/model/GrepResponseLine.java:25:public class " +
                                "GrepResponseLine {"
                ));

        // when
        List<GrepResponseLine> grepResponseLines = GitGrepResponseLineFactory
                .factorizeGrepResponseLinesList(commandOutput);

        // then
        assertThat(grepResponseLines).hasSize(2);
        assertThat(grepResponseLines.get(0))
                .hasRepository(repositoryName)
                .hasLineNumber(13)
                .hasFile("src/main/java/org/yu55/yagga/core/grep/model/GrepRequest.java")
                .hasMatchedTextLine("public class GrepRequest {");
        assertThat(grepResponseLines.get(1))
                .hasRepository(repositoryName)
                .hasLineNumber(25)
                .hasFile("src/main/java/org/yu55/yagga/core/grep/model/GrepResponseLine.java")
                .hasMatchedTextLine("public class GrepResponseLine {");
    }

    @Test
    public void shouldReturnEmptyGrepResponseLinesListWhenCommandFailed() {
        // given
        CommandOutput commandOutput = new CommandOutput("myRepository", 921);

        // when
        List<GrepResponseLine> grepResponseLines = GitGrepResponseLineFactory
                .factorizeGrepResponseLinesList(commandOutput);

        // then
        assertThat(grepResponseLines).isEmpty();
    }
}

