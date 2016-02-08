package org.yu55.yagga.utils.mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.file.Path;

import org.yu55.yagga.handler.api.DvcsRepository;
import org.yu55.yagga.handler.api.DvcsRepositoryResolver;

public class DvcsRepositoryResolverMockBehavior extends MockBehavior<DvcsRepositoryResolver> {

    private DvcsRepositoryResolverMockBehavior(DvcsRepositoryResolver mock) {
        this.mock = mock;
    }

    public static DvcsRepositoryResolverMockBehavior should(Class<DvcsRepositoryResolver> clazzToMock) {
        return new DvcsRepositoryResolverMockBehavior(mock(clazzToMock));
    }

    public DvcsRepositoryResolverMockBehavior notRecognizeRepository(Path repositoryPath) {
        when(mock.isRepository(repositoryPath)).thenReturn(false);
        return this;
    }

    public DvcsRepositoryResolverMockBehavior recognizeRepository(Path repositoryPath) {
        when(mock.isRepository(repositoryPath)).thenReturn(true);
        return this;
    }

    public DvcsRepositoryResolverMockBehavior resolveRepository(Path repositoryPath, DvcsRepository repository) {
        when(mock.resolveRepository(repositoryPath)).thenReturn(repository);
        return this;
    }

    public DvcsRepositoryResolverMockBehavior supportDvcs() {
        when(mock.isDvcsSupported()).thenReturn(true);
        return this;
    }
}
