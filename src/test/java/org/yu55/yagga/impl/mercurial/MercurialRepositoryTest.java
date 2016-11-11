package org.yu55.yagga.impl.mercurial;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.yu55.yagga.common.command.CommandExecutor;
import org.yu55.yagga.common.command.CommandOutput;
import org.yu55.yagga.common.command.CommandOutputLine;
import org.yu55.yagga.common.model.annotate.AnnotateResponse;
import org.yu55.yagga.common.model.annotate.AnnotateResponseLineAssert;
import org.yu55.yagga.common.model.grep.GrepResponseLine;
import org.yu55.yagga.common.model.grep.GrepResponseLineAssert;
import org.yu55.yagga.common.repository.annotate.AnnotateParameters;
import org.yu55.yagga.common.repository.grep.GrepParameters;
import org.yu55.yagga.impl.mercurial.command.common.MercurialCommandExecutorFactory;

public class MercurialRepositoryTest {

    @Test
    public void testRefresh() throws Exception {
        // given
        CommandExecutor executor = mock(CommandExecutor.class);
        MercurialCommandExecutorFactory commandExecutorFactory = mock(MercurialCommandExecutorFactory.class);
        Path repositoryDirectory = mock(Path.class);
        MercurialRepository repository = new MercurialRepository(repositoryDirectory, commandExecutorFactory);

        when(commandExecutorFactory.factorizeRefresh(repository)).thenReturn(executor);

        // when
        repository.refresh();

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

        CommandOutput commandOutput = new CommandOutput(repositoryDirectory.toString())
                .addOutputLine(new CommandOutputLine(
                        "sawickil 699243681ab8 Wed Jan 20 21:58:42 2016 +0100:1: Hello world!"));

        AnnotateParameters annotateParameters = new AnnotateParameters("readme.txt");

        when(commandExecutorFactory.factorizeAnnotate(repository, annotateParameters)).thenReturn(executor);
        when(executor.execute()).thenReturn(commandOutput);

        // when
        AnnotateResponse annotateResponse = repository.annotate(annotateParameters);

        // then
        verify(executor).execute();
        AnnotateResponseLineAssert.assertThat(annotateResponse.getAnnotationResponseLines().get(0))
                .hasCommitId("699243681ab8")
                .hasAuthor("sawickil")
                .hasCommitDate("Wed Jan 20 21:58:42 2016 +0100")
                .hasLineNumber(1)
                .hasLine("Hello world!");
    }

    @Test
    public void testGrep() throws Exception {
        // given
        CommandExecutor executor = mock(CommandExecutor.class);
        MercurialCommandExecutorFactory commandExecutorFactory = mock(MercurialCommandExecutorFactory.class);
        Path repositoryDirectory = Paths.get("repo");
        MercurialRepository repository = new MercurialRepository(repositoryDirectory, commandExecutorFactory);

        CommandOutput commandOutput = new CommandOutput(repositoryDirectory.toString())
                .addOutputLine(
                        new CommandOutputLine(
                                "src/main/java/org/yu55/yagga/Application.java:1:6:@SpringBootApplication"));

        GrepParameters grepParameters = new GrepParameters.Builder("buildscript").build();

        when(commandExecutorFactory.factorizeGrep(repository, grepParameters)).thenReturn(executor);
        when(executor.execute()).thenReturn(commandOutput);

        // when
        List<GrepResponseLine> grep = repository.grep(grepParameters);

        // then
        verify(executor).execute();
        Assertions.assertThat(grep.size()).isEqualTo(1);

        GrepResponseLineAssert.assertThat(grep.get(0))
                .hasFile("src/main/java/org/yu55/yagga/Application.java")
                .hasLineNumber(6)
                .hasMatchedTextLine("@SpringBootApplication")
                .hasRepository("repo");
    }

}
