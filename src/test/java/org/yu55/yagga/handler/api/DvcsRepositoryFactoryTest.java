package org.yu55.yagga.handler.api;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.yu55.yagga.util.mockito.DvcsRepositoryDescriptorMockBehavior.should;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Test;

public class DvcsRepositoryFactoryTest {

    @Test
    public void shouldNotFactorizeRepositoryForUnsupportedDirectory() {
        // given
        Path unsupportedRepoPath = Paths.get("unsupported_repository");

        DvcsRepositoryDescriptor repositoryDescriptor = should(DvcsRepositoryDescriptor.class)
                .notRecognizeRepository(unsupportedRepoPath)
                .get();

        DvcsRepositoryFactory factory = new DvcsRepositoryFactory(singletonList(repositoryDescriptor));

        // when
        Optional<DvcsRepository> optionalRepository = factory.factorizeRepository(unsupportedRepoPath);

        // then
        assertThat(optionalRepository).isEmpty();
    }

    @Test
    public void shouldFactorizeKnownRepository() {
        // given
        Path repositoryPath = Paths.get("my_repository");
        DvcsRepository repository = mock(DvcsRepository.class);

        DvcsRepositoryDescriptor repositoryDescriptor = should(DvcsRepositoryDescriptor.class)
                .recognizeAndCreateRepository(repositoryPath, repository)
                .get();

        DvcsRepositoryFactory factory = new DvcsRepositoryFactory(singletonList(repositoryDescriptor));

        // when
        Optional<DvcsRepository> optionalRepository = factory.factorizeRepository(repositoryPath);

        // then
        assertThat(optionalRepository).contains(repository);
    }

}
