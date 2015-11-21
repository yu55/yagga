package org.yu55.yagga.handler.git.command.grep;

import static org.yu55.yagga.util.assertion.CustomAssertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.yu55.yagga.core.grep.model.GrepResponseLine;
import org.yu55.yagga.handler.git.command.common.GitCommandOutput;
import org.yu55.yagga.handler.git.command.common.GitCommandOutputLine;

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
        GitCommandOutput gitCommandOutput = new GitCommandOutput(repositoryName);
        gitCommandOutput.addOutputLine(new GitCommandOutputLine(
                "src/main/java/org/yu55/yagga/core/grep/model/GrepRequest.java:13:public class GrepRequest {"
        ));
        gitCommandOutput.addOutputLine(new GitCommandOutputLine(
                "src/main/java/org/yu55/yagga/core/grep/model/GrepResponseLine.java:25:public class GrepResponseLine {"
        ));

        // when
        List<GrepResponseLine> grepResponseLines = GitGrepResponseLineFactory
                .factorizeGrepResponseLinesList(gitCommandOutput);

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
        GitCommandOutput gitCommandOutput = new GitCommandOutput("myRepository", 921);

        // when
        List<GrepResponseLine> grepResponseLines = GitGrepResponseLineFactory
                .factorizeGrepResponseLinesList(gitCommandOutput);

        // then
        assertThat(grepResponseLines).isEmpty();
    }
}

