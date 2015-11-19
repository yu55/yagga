package org.yu55.yagga.handler.git.command.grep;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.yu55.yagga.core.grep.model.GrepResponseAssert.assertThat;
import static org.yu55.yagga.handler.git.command.grep.GitGrepHandlerTest.GitRepositoryMockBehavior.should;

import java.util.List;

import org.junit.Test;
import org.yu55.yagga.core.grep.model.GrepRequest;
import org.yu55.yagga.core.grep.model.GrepResponse;
import org.yu55.yagga.core.grep.model.GrepResponseLine;
import org.yu55.yagga.handler.git.GitRepositories;
import org.yu55.yagga.handler.git.GitRepository;

public class GitGrepHandlerTest {

    @Test
    public void shouldReturnGrepResponseLines() {
        // given
        GrepResponseLine matchingResponseLine =
                new GrepResponseLine("myRepository", "Hello.java", 5, "public class Hello {");

        GitRepository matchingRepository = should(GitRepository.class)
                .returnDirectoryName("myRepository")
                .returnGrepResponse(asList(matchingResponseLine))
                .get();

        GrepResponseLine otherResponseLine =
                new GrepResponseLine("otherRepository", "Bye.java", 3, "public class Bye {");

        GitRepository otherRepository = should(GitRepository.class)
                .returnDirectoryName("otherRepository")
                .returnGrepResponse(asList(otherResponseLine))
                .get();

        GitRepositories repositories = mock(GitRepositories.class);
        when(repositories.getRepositories())
                .thenReturn(asList(matchingRepository, otherRepository));

        GitGrepHandler grepHandler = new GitGrepHandler(repositories);

        GrepRequest request = new GrepRequest("class", asList("myRepository"), true);

        // when
        GrepResponse response = grepHandler.grep(request);

        // then
        assertThat(response).hasOnlyGrepResponseLines(matchingResponseLine);
    }

    // This is a proof of concept

    static abstract class MockBehavior<T> {

        protected T mock;

        public T get() {
            return mock;
        }
    }

    static class GitRepositoryMockBehavior extends MockBehavior<GitRepository> {

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
}
