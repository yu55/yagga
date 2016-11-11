package org.yu55.yagga.utils.mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.yu55.yagga.common.model.grep.GrepResponseLine;
import org.yu55.yagga.common.repository.Repository;
import org.yu55.yagga.common.repository.grep.GrepParameters;

public class RepositoryMockBehavior extends MockBehavior<Repository> {

    private RepositoryMockBehavior(Repository repository) {
        this.mock = repository;
    }

    public static RepositoryMockBehavior should(Class<Repository> clazzToMock) {
        return new RepositoryMockBehavior(mock(clazzToMock));
    }

    public RepositoryMockBehavior returnDirectoryName(String name) {
        when(mock.getDirectoryName()).thenReturn(name);
        return this;
    }

    public RepositoryMockBehavior returnGrepResponse(List<GrepResponseLine> grepResponseLineList) {
        when(mock.grep(any(GrepParameters.class)))
                .thenReturn(grepResponseLineList);
        return this;
    }
}
