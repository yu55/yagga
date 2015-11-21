package org.yu55.yagga.handler.git;

import org.junit.Test;
import org.yu55.yagga.core.annotate.model.AnnotateResponse;
import org.yu55.yagga.core.grep.model.GrepResponseLine;
import org.yu55.yagga.handler.api.command.annotate.AnnotateParameters;
import org.yu55.yagga.handler.api.command.grep.GrepParameters;
import org.yu55.yagga.handler.generic.command.CommandExecutor;
import org.yu55.yagga.handler.generic.command.CommandOutput;
import org.yu55.yagga.handler.generic.command.CommandOutputLine;
import org.yu55.yagga.handler.git.command.common.GitCommandExecutorFactory;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GitRepositoryTest {

    @Test
    public void testPull() throws Exception {
        // given
        CommandExecutor executor = mock(CommandExecutor.class);
        GitCommandExecutorFactory commandExecutorFactory = mock(GitCommandExecutorFactory.class);
        File file = mock(File.class);
        GitRepository repository = new GitRepository(file, commandExecutorFactory);

        when(commandExecutorFactory.factorizePull(repository)).thenReturn(executor);

        // when
        repository.pull();

        // then
        verify(executor).execute();
    }

    @Test
    public void testAnnotate() throws Exception {
        // given
        CommandExecutor executor = mock(CommandExecutor.class);
        GitCommandExecutorFactory commandExecutorFactory = mock(GitCommandExecutorFactory.class);
        File file = mock(File.class);
        GitRepository repository = new GitRepository(file, commandExecutorFactory);
        CommandOutput commandOutput = new CommandOutput(file.getName());

        AnnotateParameters annotateParameters = new AnnotateParameters("build.gradle");

        when(commandExecutorFactory.factorizeAnnotate(repository, annotateParameters)).thenReturn(executor);
        when(executor.execute()).thenReturn(commandOutput);
        commandOutput.addOutputLine(new CommandOutputLine(
                "716ec6a6        (  Marcin P     2015-09-17 21:23:13 +0200       1)buildscript {"));

        // when
        AnnotateResponse annotateResponse = repository.annotate(annotateParameters);

        // then
        verify(executor).execute();
        assertThat(annotateResponse.getAnnotations()).contains("Marcin P");
        assertThat(annotateResponse.getFileContent()).contains("buildscript");
    }

    @Test
    public void testGrep() throws Exception {
        // given
        CommandExecutor executor = mock(CommandExecutor.class);
        GitCommandExecutorFactory commandExecutorFactory = mock(GitCommandExecutorFactory.class);;
        File repositoryDirectory = mock(File.class);
        when(repositoryDirectory.getName()).thenReturn("repo");
        GitRepository repository = new GitRepository(repositoryDirectory, commandExecutorFactory);
        CommandOutput commandOutput = new CommandOutput(repositoryDirectory.getName());

        String wanted = "buildscript";
        boolean ignoreCase = false;
        GrepParameters grepParameters = new GrepParameters(wanted, ignoreCase);

        when(commandExecutorFactory.factorizeGrep(repository, grepParameters)).thenReturn(executor);
        when(executor.execute()).thenReturn(commandOutput);
        commandOutput.addOutputLine(new CommandOutputLine("build.gradle:1:buildscript {"));

        // when
        List<GrepResponseLine> grep = repository.grep(grepParameters);

        // then
        verify(executor).execute();
        assertThat(grep.size()).isEqualTo(1);
        GrepResponseLine grepResponseLine = grep.get(0);
        assertThat(grepResponseLine.getFile()).isEqualTo("build.gradle");
        assertThat(grepResponseLine.getLineNumber()).isEqualTo(1);
        assertThat(grepResponseLine.getMatchedTextLine()).isEqualTo("buildscript {");
        assertThat(grepResponseLine.getRepository()).isEqualTo("repo");
    }
}
