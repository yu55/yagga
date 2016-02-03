package org.yu55.yagga.handler.git;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;
import org.yu55.yagga.core.annotate.model.AnnotateResponse;
import org.yu55.yagga.core.annotate.model.AnnotateResponseLine;
import org.yu55.yagga.core.grep.model.GrepResponseLine;
import org.yu55.yagga.handler.api.command.annotate.AnnotateParameters;
import org.yu55.yagga.handler.api.command.grep.GrepParameters;
import org.yu55.yagga.handler.generic.command.CommandExecutor;
import org.yu55.yagga.handler.generic.command.CommandOutput;
import org.yu55.yagga.handler.generic.command.CommandOutputLine;
import org.yu55.yagga.handler.git.command.common.GitCommandExecutorFactory;

public class GitRepositoryTest {

    @Test
    public void testPull() throws Exception {
        // given
        CommandExecutor executor = mock(CommandExecutor.class);
        GitCommandExecutorFactory commandExecutorFactory = mock(GitCommandExecutorFactory.class);
        Path repositoryDirectory = mock(Path.class);
        GitRepository repository = new GitRepository(repositoryDirectory, commandExecutorFactory);

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
        Path repositoryDirectory = mock(Path.class);
        GitRepository repository = new GitRepository(repositoryDirectory, commandExecutorFactory);
        CommandOutput commandOutput = new CommandOutput(repositoryDirectory.toString())
                .addOutputLine(new CommandOutputLine(
                        "716ec6a6        (  Marcin P     2015-09-17 21:23:13 +0200       1)buildscript {"));

        AnnotateParameters annotateParameters = new AnnotateParameters("build.gradle");

        when(commandExecutorFactory.factorizeAnnotate(repository, annotateParameters)).thenReturn(executor);
        when(executor.execute()).thenReturn(commandOutput);

        // when
        AnnotateResponse annotateResponse = repository.annotate(annotateParameters);

        // then
        verify(executor).execute();
        AnnotateResponseLine annotateLine = annotateResponse.getAnnotationResponseLines().get(0);
        assertThat(annotateLine.getCommitId()).isEqualTo("716ec6a6");
        assertThat(annotateLine.getAuthor()).isEqualTo("Marcin P");
        assertThat(annotateLine.getCommitDate()).isEqualTo("2015-09-17 21:23:13 +0200");
        assertThat(annotateLine.getLineNumber()).isEqualTo(1);
        assertThat(annotateLine.getLine()).isEqualTo("buildscript {");
    }

    @Test
    public void testGrep() throws Exception {
        // given
        CommandExecutor executor = mock(CommandExecutor.class);
        GitCommandExecutorFactory commandExecutorFactory = mock(GitCommandExecutorFactory.class);

        Path repositoryDirectory = Paths.get("repo");
        GitRepository repository = new GitRepository(repositoryDirectory, commandExecutorFactory);
        CommandOutput commandOutput = new CommandOutput(repositoryDirectory.toString())
                .addOutputLine(new CommandOutputLine("build.gradle:1:buildscript {"));

        GrepParameters grepParameters = new GrepParameters.Builder("buildscript").build();

        when(commandExecutorFactory.factorizeGrep(repository, grepParameters)).thenReturn(executor);
        when(executor.execute()).thenReturn(commandOutput);

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
