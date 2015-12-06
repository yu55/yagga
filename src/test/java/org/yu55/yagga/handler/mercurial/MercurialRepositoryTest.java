package org.yu55.yagga.handler.mercurial;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;
import org.yu55.yagga.core.annotate.model.AnnotateResponse;
import org.yu55.yagga.core.grep.model.GrepResponseLine;
import org.yu55.yagga.handler.api.command.annotate.AnnotateParameters;
import org.yu55.yagga.handler.api.command.grep.GrepParameters;
import org.yu55.yagga.handler.generic.command.CommandExecutor;
import org.yu55.yagga.handler.generic.command.CommandOutput;
import org.yu55.yagga.handler.generic.command.CommandOutputLine;
import org.yu55.yagga.handler.mercurial.command.common.MercurialCommandExecutorFactory;

public class MercurialRepositoryTest {

    @Test
    public void testPull() throws Exception {
        // given
        CommandExecutor executor = mock(CommandExecutor.class);
        MercurialCommandExecutorFactory commandExecutorFactory = mock(MercurialCommandExecutorFactory.class);
        Path repositoryDirectory = mock(Path.class);
        MercurialRepository repository = new MercurialRepository(repositoryDirectory, commandExecutorFactory);

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
        MercurialCommandExecutorFactory commandExecutorFactory = mock(MercurialCommandExecutorFactory.class);
        Path repositoryDirectory = mock(Path.class);
        MercurialRepository repository = new MercurialRepository(repositoryDirectory, commandExecutorFactory);

        CommandOutput commandOutput = new CommandOutput(repositoryDirectory.toString());

        AnnotateParameters annotateParameters = new AnnotateParameters("build.gradle");

        when(commandExecutorFactory.factorizeAnnotate(repository, annotateParameters)).thenReturn(executor);
        when(executor.execute()).thenReturn(commandOutput);
        commandOutput.addOutputLine(new CommandOutputLine(
                "user 1 Sat Nov 21 21:55:58 2015 +0100: 7: public class Application {"));

        // when
        AnnotateResponse annotateResponse = repository.annotate(annotateParameters);

        // then
        verify(executor).execute();
        assertThat(annotateResponse.getAnnotations()).contains("user");
        assertThat(annotateResponse.getFileContent()).contains("public class Application {");
    }

    @Test
    public void testGrep() throws Exception {
        // given
        CommandExecutor executor = mock(CommandExecutor.class);
        MercurialCommandExecutorFactory commandExecutorFactory = mock(MercurialCommandExecutorFactory.class);
        Path repositoryDirectory = Paths.get("repo");
        MercurialRepository repository = new MercurialRepository(repositoryDirectory, commandExecutorFactory);

        CommandOutput commandOutput = new CommandOutput(repositoryDirectory.toString());

        String wanted = "buildscript";
        boolean ignoreCase = false;
        GrepParameters grepParameters = new GrepParameters(wanted, ignoreCase, null);

        when(commandExecutorFactory.factorizeGrep(repository, grepParameters)).thenReturn(executor);
        when(executor.execute()).thenReturn(commandOutput);
        commandOutput.addOutputLine(
                new CommandOutputLine("src/main/java/org/yu55/yagga/Application.java:1:6:@SpringBootApplication"));

        // when
        List<GrepResponseLine> grep = repository.grep(grepParameters);

        // then
        verify(executor).execute();
        assertThat(grep.size()).isEqualTo(1);
        GrepResponseLine grepResponseLine = grep.get(0);
        assertThat(grepResponseLine.getFile()).isEqualTo("src/main/java/org/yu55/yagga/Application.java");
        assertThat(grepResponseLine.getLineNumber()).isEqualTo(6);
        assertThat(grepResponseLine.getMatchedTextLine()).isEqualTo("@SpringBootApplication");
        assertThat(grepResponseLine.getRepository()).isEqualTo("repo");
    }

}
