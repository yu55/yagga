package org.yu55.yagga.handler.git.command.grep;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.exec.CommandLine;
import org.junit.Test;
import org.yu55.yagga.handler.api.command.grep.GrepParameters;

public class GitGrepCommandTest {

    @Test
    public void shouldProduceCommandWithMinimumArgumentsSet() throws Exception {
        // given
        GrepParameters parameters = new GrepParameters("text", false, null);
        GitGrepCommand command = new GitGrepCommand(parameters);

        // when
        CommandLine commandLine = command.getCommandLine();

        // then
        assertThat(commandLine.getArguments()).containsExactly("grep", "-n", "-I", "text");
    }

    @Test
    public void shouldProduceCommandWithIgnoringCase() throws Exception {
        // given
        GrepParameters parameters = new GrepParameters("text", true, null);
        GitGrepCommand command = new GitGrepCommand(parameters);

        // when
        CommandLine commandLine = command.getCommandLine();

        // then
        assertThat(commandLine.getArguments()).containsExactly("grep", "-n", "-I", "-i", "text");
    }

    @Test
    public void shouldProduceCommandWithFilteringByFilename() throws Exception {
        // given
        GrepParameters parameters = new GrepParameters("text", false, "*.java");
        GitGrepCommand command = new GitGrepCommand(parameters);

        // when
        CommandLine commandLine = command.getCommandLine();

        // then
        assertThat(commandLine.getArguments()).containsExactly("grep", "-n", "-I", "text", "*.java");
    }

    @Test
    public void shouldProduceCommandWithEscapedSpaceChar() throws Exception {
        // given
        GrepParameters parameters = new GrepParameters(" text", false, null);
        GitGrepCommand command = new GitGrepCommand(parameters);

        // when
        CommandLine commandLine = command.getCommandLine();

        // then
        assertThat(commandLine.getArguments()).containsExactly("grep", "-n", "-I", "\\ text");
    }

    @Test
    public void shouldProduceCommandWithEscapedAsteriskChar() throws Exception {
        // given
        GrepParameters parameters = new GrepParameters("*text", false, null);
        GitGrepCommand command = new GitGrepCommand(parameters);

        // when
        CommandLine commandLine = command.getCommandLine();

        // then
        assertThat(commandLine.getArguments()).containsExactly("grep", "-n", "-I", "\\*text");
    }

    @Test
    public void shouldProduceCommandWithEscapedHyphenChar() throws Exception {
        // given
        GrepParameters parameters = new GrepParameters("-text", false, null);
        GitGrepCommand command = new GitGrepCommand(parameters);

        // when
        CommandLine commandLine = command.getCommandLine();

        // then
        assertThat(commandLine.getArguments()).containsExactly("grep", "-n", "-I", "\\-text");
    }

}
