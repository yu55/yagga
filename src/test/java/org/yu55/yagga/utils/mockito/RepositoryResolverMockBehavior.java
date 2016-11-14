package org.yu55.yagga.utils.mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.file.Path;

import org.yu55.yagga.common.repository.Repository;
import org.yu55.yagga.common.repository.RepositoryResolver;

public class RepositoryResolverMockBehavior extends MockBehavior<RepositoryResolver> {

    private RepositoryResolverMockBehavior(RepositoryResolver mock) {
        this.mock = mock;
    }

    public static RepositoryResolverMockBehavior should(Class<RepositoryResolver> clazzToMock) {
        return new RepositoryResolverMockBehavior(mock(clazzToMock));
    }

    public RepositoryResolverMockBehavior notRecognizeRepository(Path repositoryPath) {
        when(mock.isRepository(repositoryPath)).thenReturn(false);
        return this;
    }

    public RepositoryResolverMockBehavior recognizeRepository(Path repositoryPath) {
        when(mock.isRepository(repositoryPath)).thenReturn(true);
        return this;
    }

    public RepositoryResolverMockBehavior resolveRepository(Path repositoryPath, Repository repository) {
        when(mock.resolveRepository(repositoryPath)).thenReturn(repository);
        return this;
    }

    public RepositoryResolverMockBehavior support() {
        when(mock.isSupported()).thenReturn(true);
        return this;
    }
}
