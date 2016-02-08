package org.yu55.yagga.utils.mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.yu55.yagga.core.grep.model.GrepResponseLine;
import org.yu55.yagga.handler.api.DvcsRepository;
import org.yu55.yagga.handler.api.command.grep.GrepParameters;

public class DvcsRepositoryMockBehavior extends MockBehavior<DvcsRepository> {

    private DvcsRepositoryMockBehavior(DvcsRepository repository) {
        this.mock = repository;
    }

    public static DvcsRepositoryMockBehavior should(Class<DvcsRepository> clazzToMock) {
        return new DvcsRepositoryMockBehavior(mock(clazzToMock));
    }

    public DvcsRepositoryMockBehavior returnDirectoryName(String name) {
        when(mock.getDirectoryName()).thenReturn(name);
        return this;
    }

    public DvcsRepositoryMockBehavior returnGrepResponse(List<GrepResponseLine> grepResponseLineList) {
        when(mock.grep(any(GrepParameters.class)))
                .thenReturn(grepResponseLineList);
        return this;
    }
}
