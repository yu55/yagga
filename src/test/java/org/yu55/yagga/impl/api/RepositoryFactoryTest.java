package org.yu55.yagga.impl.api;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.yu55.yagga.utils.mockito.RepositoryResolverMockBehavior.should;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Test;
import org.yu55.yagga.common.repository.Repository;
import org.yu55.yagga.common.repository.RepositoryFactory;
import org.yu55.yagga.common.repository.RepositoryResolver;

public class RepositoryFactoryTest {

    @Test
    public void shouldNotFactorizeRepositoryForUnsupportedDirectory() {
        // given
        Path unsupportedRepoPath = Paths.get("unsupported_repository");

        RepositoryResolver repositoryResolver = should(RepositoryResolver.class)
                .notRecognizeRepository(unsupportedRepoPath)
                .get();

        RepositoryFactory factory = new RepositoryFactory(singletonList(repositoryResolver));

        // when
        Optional<Repository> optionalRepository = factory.factorizeRepository(unsupportedRepoPath);

        // then
        assertThat(optionalRepository).isEmpty();
    }

    @Test
    public void shouldFactorizeKnownRepository() {
        // given
        Path repositoryPath = Paths.get("my_repository");
        Repository repository = mock(Repository.class);

        RepositoryResolver repositoryResolver = should(RepositoryResolver.class)
                .support()
                .recognizeRepository(repositoryPath)
                .resolveRepository(repositoryPath, repository)
                .get();

        RepositoryFactory factory = new RepositoryFactory(singletonList(repositoryResolver));

        // when
        Optional<Repository> optionalRepository = factory.factorizeRepository(repositoryPath);

        // then
        assertThat(optionalRepository).contains(repository);
    }

}
