package org.yu55.yagga.handler.git;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.yu55.yagga.core.annotate.model.AnnotateResponse;
import org.yu55.yagga.core.grep.model.GrepResponseLine;
import org.yu55.yagga.handler.git.command.common.GitCommandExecutor;
import org.yu55.yagga.handler.git.command.common.GitCommandExecutorFactory;
import org.yu55.yagga.handler.git.command.common.GitCommandOutput;
import org.yu55.yagga.handler.git.command.common.GitCommandOutputLine;

public class GitRepositoryTest {

    @Test
    public void testPull() throws Exception {
        // given
        GitCommandExecutor executor = mock(GitCommandExecutor.class);
        GitCommandExecutorFactory commandExecutorFactory = mock(GitCommandExecutorFactory.class);
        File file = mock(File.class);
        GitRepository repository = new GitRepository(file, commandExecutorFactory);

        when(commandExecutorFactory.factorizePull()).thenReturn(executor);

        // when
        repository.pull();

        // then
        verify(executor).execute(file);
    }

    @Test
    public void testAnnotate() throws Exception {
        // given
        GitCommandExecutor executor = mock(GitCommandExecutor.class);
        GitCommandExecutorFactory commandExecutorFactory = mock(GitCommandExecutorFactory.class);
        File file = mock(File.class);
        GitRepository repository = new GitRepository(file, commandExecutorFactory);
        GitCommandOutput gitCommandOutput = new GitCommandOutput(file.getName());

        String fileToAnnotate = "build.gradle";
        when(commandExecutorFactory.factorizeAnnotate(fileToAnnotate)).thenReturn(executor);
        when(executor.execute(file)).thenReturn(gitCommandOutput);
        gitCommandOutput.addOutputLine(new GitCommandOutputLine(
                "716ec6a6        (  Marcin P     2015-09-17 21:23:13 +0200       1)buildscript {"));

        // when
        AnnotateResponse annotateResponse = repository.annotate(fileToAnnotate);

        // then
        verify(executor).execute(file);
        assertThat(annotateResponse.getAnnotations()).contains("Marcin P");
        assertThat(annotateResponse.getFileContent()).contains("buildscript");
    }

    @Test
    public void testGrep() throws Exception {
        // given
        GitCommandExecutor executor = mock(GitCommandExecutor.class);
        GitCommandExecutorFactory commandExecutorFactory = mock(GitCommandExecutorFactory.class);
        File repositoryDirectory = mock(File.class);
        GitRepository repository = new GitRepository(repositoryDirectory, commandExecutorFactory);
        GitCommandOutput gitCommandOutput = new GitCommandOutput(repositoryDirectory.getName());

        String wanted = "buildscript";
        when(commandExecutorFactory.factorizeGrep(wanted)).thenReturn(executor);
        when(executor.execute(repositoryDirectory)).thenReturn(gitCommandOutput);
        when(repositoryDirectory.getName()).thenReturn("repo");
        gitCommandOutput.addOutputLine(new GitCommandOutputLine(
                "build.gradle:1:buildscript {"));
        // when
        List<GrepResponseLine> grep = repository.grep(wanted);

        // then
        verify(executor).execute(repositoryDirectory);
        assertThat(grep.size()).isEqualTo(1);
        GrepResponseLine grepResponseLine =grep.get(0);
        assertThat(grepResponseLine.getFile()).isEqualTo("build.gradle");
        assertThat(grepResponseLine.getLineNumber()).isEqualTo(1);
        assertThat(grepResponseLine.getMatchedTextLine()).isEqualTo("buildscript {");
        assertThat(grepResponseLine.getRepository()).isEqualTo("repo");
    }
}
