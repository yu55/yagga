package org.yu55.yagga.util.mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.file.Path;

import org.yu55.yagga.handler.api.DvcsRepository;
import org.yu55.yagga.handler.api.DvcsRepositoryDescriptor;

public class DvcsRepositoryDescriptorMockBehavior extends MockBehavior<DvcsRepositoryDescriptor> {

    private DvcsRepositoryDescriptorMockBehavior(DvcsRepositoryDescriptor mock) {
        this.mock = mock;
    }

    public static DvcsRepositoryDescriptorMockBehavior should(Class<DvcsRepositoryDescriptor> clazzToMock) {
        return new DvcsRepositoryDescriptorMockBehavior(mock(clazzToMock));
    }

    public DvcsRepositoryDescriptorMockBehavior notRecognizeRepository(Path repositoryPath) {
        when(mock.isRepository(repositoryPath)).thenReturn(false);
        return this;
    }

    public DvcsRepositoryDescriptorMockBehavior recognizeAndCreateRepository(Path repositoryPath,
                                                                             DvcsRepository repository) {
        when(mock.isRepository(repositoryPath)).thenReturn(true);
        when(mock.createRepository(repositoryPath)).thenReturn(repository);
        return this;
    }

}
