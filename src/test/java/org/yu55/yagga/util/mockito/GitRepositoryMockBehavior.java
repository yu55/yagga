package org.yu55.yagga.util.mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.yu55.yagga.core.grep.model.GrepResponseLine;
import org.yu55.yagga.handler.git.GitRepository;
import org.yu55.yagga.handler.git.command.grep.GitGrepCommandOptions;

public class GitRepositoryMockBehavior extends MockBehavior<GitRepository> {

    private GitRepositoryMockBehavior(GitRepository repository) {
        this.mock = repository;
    }

    public static GitRepositoryMockBehavior should(Class<GitRepository> clazzToMock) {
        return new GitRepositoryMockBehavior(mock(clazzToMock));
    }

    public GitRepositoryMockBehavior returnDirectoryName(String name) {
        when(mock.getDirectoryName()).thenReturn(name);
        return this;
    }

    public GitRepositoryMockBehavior returnGrepResponse(List<GrepResponseLine> grepResponseLineList) {
        when(mock.grep(anyString(), any(GitGrepCommandOptions.class)))
                .thenReturn(grepResponseLineList);
        return this;
    }
}
