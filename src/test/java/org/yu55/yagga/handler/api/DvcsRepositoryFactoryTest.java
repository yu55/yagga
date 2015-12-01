package org.yu55.yagga.handler.api;

import static java.nio.file.Files.createTempDirectory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.Test;
import org.yu55.yagga.handler.api.DvcsRepositoryFactory.RepositoryInstantiationException;
import org.yu55.yagga.handler.git.command.common.GitCommandExecutorFactory;
import org.yu55.yagga.handler.mercurial.command.common.MercurialCommandExecutorFactory;

public class DvcsRepositoryFactoryTest {

    @Test
    public void shouldNotFactorizeRepositoryForUnsupportedDirectory() throws IOException,
            RepositoryInstantiationException {
        // given
        Path repositoryDir = createTempDirectory("unsupported_repository");
        DvcsRepositoryFactory factory = new DvcsRepositoryFactory(
                mock(GitCommandExecutorFactory.class), mock(MercurialCommandExecutorFactory.class));

        // when
        Throwable exception = catchThrowable(() -> factory.factorizeRepository(repositoryDir.toFile()));

        // then
        assertThat(exception).hasMessage(repositoryDir.toString() + " is not recognized as a repository");
    }
}
