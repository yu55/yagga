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
        GitCommandOutput gitCommandOutput = new GitCommandOutput("myRepository");
        gitCommandOutput.addOutputLine(new GitCommandOutputLine(
                "src/main/java/org/yu55/yagga/core/grep/model/GrepRequest.java:13:public class GrepRequest {"
        ));
        gitCommandOutput.addOutputLine(new GitCommandOutputLine(
                "src/main/java/org/yu55/yagga/core/grep/model/GrepResponseLine.java:public class GrepResponseLine {"
        ));

        // when
        List<GrepResponseLine> grepResponseLines = GitGrepResponseLineFactory
                .factorizeGrepResponseLinesList(gitCommandOutput);

        // then
        assertThat(grepResponseLines).hasSize(2);
        //TODO: finish me
    }
}
