package org.yu55.yagga.impl.mercurial.command.grep;

import static org.yu55.yagga.utils.assertion.CustomAssertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.yu55.yagga.api.grep.model.GrepResponseLine;
import org.yu55.yagga.common.command.CommandOutput;
import org.yu55.yagga.common.command.CommandOutputLine;

public class MercurialGrepResponseLineFactoryTest {

    @Test
    public void shouldReturnGrepResponseLine() throws Exception {
        // given
        String grepOutputLine =
                "src/main/java/org/yu55/yagga/Application.java:1:6:@SpringBootApplication";
        String repositoryName = "myRepository";

        // when
        GrepResponseLine expected = MercurialGrepResponseLineFactory.factorizeGrepResponseLine(repositoryName,
                grepOutputLine);

        // then
        assertThat(expected)
                .hasRepository(repositoryName)
                .hasLineNumber(6)
                .hasFile("src/main/java/org/yu55/yagga/Application.java")
                .hasMatchedTextLine("@SpringBootApplication");
    }

    @Test
    public void shouldReturnGrepResponseLinesList() {
        // given
        String repositoryName = "myRepository";
        CommandOutput commandOutput = new CommandOutput(repositoryName)
                .addOutputLine(new CommandOutputLine(
                        "src/main/java/org/yu55/yagga/Application.java:1:6:@SpringBootApplication"
                ))
                .addOutputLine(new CommandOutputLine(
                        "src/main/java/org/yu55/yagga/ApplicationTest.java:1:8:public class ApplicationTest {"
                ));

        // when
        List<GrepResponseLine> grepResponseLines = MercurialGrepResponseLineFactory
                .factorizeGrepResponseLinesList(commandOutput);

        // then
        assertThat(grepResponseLines).hasSize(2);
        assertThat(grepResponseLines.get(0))
                .hasRepository(repositoryName)
                .hasLineNumber(6)
                .hasFile("src/main/java/org/yu55/yagga/Application.java")
                .hasMatchedTextLine("@SpringBootApplication");
        assertThat(grepResponseLines.get(1))
                .hasRepository(repositoryName)
                .hasLineNumber(8)
                .hasFile("src/main/java/org/yu55/yagga/ApplicationTest.java")
                .hasMatchedTextLine("public class ApplicationTest {");
    }

    @Test
    public void shouldReturnEmptyGrepResponseLinesListWhenCommandFailed() {
        // given
        CommandOutput commandOutput = new CommandOutput("myRepository", 1);

        // when
        List<GrepResponseLine> grepResponseLines = MercurialGrepResponseLineFactory
                .factorizeGrepResponseLinesList(commandOutput);

        // then
        assertThat(grepResponseLines).isEmpty();
    }
}

