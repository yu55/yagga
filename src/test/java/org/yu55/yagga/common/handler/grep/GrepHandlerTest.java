package org.yu55.yagga.common.handler.grep;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.yu55.yagga.api.grep.model.GrepResponseAssert.assertThat;
import static org.yu55.yagga.utils.mockito.RepositoryMockBehavior.should;

import org.junit.Test;
import org.yu55.yagga.api.grep.model.GrepRequest;
import org.yu55.yagga.api.grep.model.GrepResponse;
import org.yu55.yagga.api.grep.model.GrepResponseLine;
import org.yu55.yagga.common.handler.GrepHandler;
import org.yu55.yagga.common.repository.Repository;
import org.yu55.yagga.common.repository.RepositoriesContainer;

public class GrepHandlerTest {

    @Test
    public void shouldReturnGrepResponseLines() {
        // given
        GrepResponseLine matchingResponseLine =
                new GrepResponseLine("myRepository", "Hello.java", 5, "public class Hello {");

        Repository matchingRepository = should(Repository.class)
                .returnDirectoryName("myRepository")
                .returnGrepResponse(asList(matchingResponseLine))
                .get();

        GrepResponseLine otherResponseLine =
                new GrepResponseLine("otherRepository", "Bye.java", 3, "public class Bye {");

        Repository otherRepository = should(Repository.class)
                .returnDirectoryName("otherRepository")
                .returnGrepResponse(asList(otherResponseLine))
                .get();

        RepositoriesContainer repositoriesContainer = mock(RepositoriesContainer.class);
        when(repositoriesContainer.getRepositories())
                .thenReturn(asList(matchingRepository, otherRepository));

        GrepHandler grepHandler = new GrepHandler(repositoriesContainer);

        GrepRequest request = new GrepRequest.Builder("class", asList("myRepository")).ignoreCase(true).build();

        // when
        GrepResponse response = grepHandler.grep(request);

        // then
        assertThat(response).hasOnlyGrepResponseLines(matchingResponseLine);
    }

}
