package org.yu55.yagga.handler.git.command;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.Executor;
import org.junit.Test;
import org.yu55.yagga.handler.git.command.common.GitCommand;
import org.yu55.yagga.handler.git.command.common.GitCommandExecutor;
import org.yu55.yagga.handler.git.command.common.GitCommandOutput;
import org.yu55.yagga.handler.git.command.common.GitCommandOutputLine;

public class GitCommandExecutorTest {

    @Test
    public void shouldExecuteCommand() throws IOException {
        // given
        Executor executor = mock(Executor.class);
        when(executor.getWorkingDirectory()).thenReturn(new File("/my/repo"));
        GitCommandOutput output = new GitCommandOutput(executor.getWorkingDirectory().getName());
        output.addOutputLine(new GitCommandOutputLine("First line."));
        output.addOutputLine(new GitCommandOutputLine("Second line."));
        GitCommandExecutor gitCommandExecutor = new GitCommandExecutor(mock(GitCommand.class), executor, () -> output);

        // when
        GitCommandOutput expectedOutput = gitCommandExecutor.execute();

        // then
        verify(executor).execute(any(CommandLine.class));
        assertThat(expectedOutput.getRepositoryName()).isEqualTo("repo");
        assertThat(expectedOutput.getOutputLines().stream().map(GitCommandOutputLine::getLine).collect(toList()))
                .containsExactly("First line.", "Second line.");
    }

}
