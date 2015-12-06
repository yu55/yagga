package org.yu55.yagga.handler.generic.command;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.Executor;
import org.junit.Test;

public class CommandExecutorTest {

    @Test
    public void shouldExecuteCommand() throws IOException {
        // given
        Executor executor = mock(Executor.class);
        when(executor.getWorkingDirectory()).thenReturn(Paths.get("/my/repo").toFile());
        CommandOutput output = new CommandOutput(executor.getWorkingDirectory().getName());
        output.addOutputLine(new CommandOutputLine("First line."));
        output.addOutputLine(new CommandOutputLine("Second line."));
        CommandExecutor commandExecutor = new CommandExecutor(mock(Command.class), executor, () -> output);

        // when
        CommandOutput expectedOutput = commandExecutor.execute();

        // then
        verify(executor).execute(any(CommandLine.class));
        assertThat(expectedOutput.getRepositoryName()).isEqualTo("repo");
        assertThat(expectedOutput.getOutputLines().stream().map(CommandOutputLine::getLine).collect(toList()))
                .containsExactly("First line.", "Second line.");
    }

}
