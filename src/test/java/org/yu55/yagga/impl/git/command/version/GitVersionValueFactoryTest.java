package org.yu55.yagga.impl.git.command.version;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.yu55.yagga.impl.git.command.version.GitVersionValueFactory.factorizeGitVersionValue;

import org.junit.Test;
import org.yu55.yagga.common.command.CommandOutput;
import org.yu55.yagga.common.command.CommandOutputLine;

public class GitVersionValueFactoryTest {

    @Test
    public void shouldReturnGitVersionValue() {
        // given
        CommandOutput commandOutput = new CommandOutput()
                .addOutputLine(new CommandOutputLine("git version 2.5.0"));

        // when
        int version = factorizeGitVersionValue(commandOutput);

        // then
        assertThat(version).isEqualTo(250);
    }

    @Test
    public void shouldThrowExceptionForEmptyCommandOutput() {
        // given
        CommandOutput commandOutput = new CommandOutput();

        // when
        Throwable throwable = catchThrowable(() -> factorizeGitVersionValue(commandOutput));

        // then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class).hasMessage("Cannot parse git version.");
    }

    @Test
    public void shouldThrowExceptionFoIncorrectCommandOutput() {
        // given
        CommandOutput commandOutput = new CommandOutput()
                .addOutputLine(new CommandOutputLine("incorrect git version string"));

        // when
        Throwable throwable = catchThrowable(() -> factorizeGitVersionValue(commandOutput));

        // then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class).hasMessage("Cannot parse git version.");
    }
}
