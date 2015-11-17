package org.yu55.yagga.handler.git.command.grep;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.yu55.yagga.core.grep.model.GrepResponseLine;

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
        assertThat(expected.getRepository()).isEqualTo("myRepository");
        assertThat(expected.getLineNumber()).isEqualTo(13);
        assertThat(expected.getFile()).isEqualTo("src/main/java/org/yu55/yagga/core/grep/model/GrepRequest.java");
        assertThat(expected.getMatchedTextLine()).isEqualTo("public class GrepRequest {");
    }
}
