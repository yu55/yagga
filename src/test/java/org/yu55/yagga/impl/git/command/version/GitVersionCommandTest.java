package org.yu55.yagga.impl.git.command.version;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.exec.CommandLine;
import org.junit.Test;

public class GitVersionCommandTest {

    @Test
    public void shouldProduceCommandWithMinimumArgumentsSet() throws Exception {
        // given
        GitVersionCommand command = new GitVersionCommand();

        // when
        CommandLine commandLine = command.getCommandLine();

        // then
        assertThat(commandLine.getArguments()).containsExactly("--version");
    }
}
