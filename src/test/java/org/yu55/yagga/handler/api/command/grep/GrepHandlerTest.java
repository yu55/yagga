package org.yu55.yagga.handler.api.command.grep;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.yu55.yagga.core.grep.model.GrepResponseAssert.assertThat;
import static org.yu55.yagga.util.mockito.DvcsRepositoryMockBehavior.should;

import org.junit.Test;
import org.yu55.yagga.core.grep.model.GrepRequest;
import org.yu55.yagga.core.grep.model.GrepResponse;
import org.yu55.yagga.core.grep.model.GrepResponseLine;
import org.yu55.yagga.handler.api.DvcsRepository;
import org.yu55.yagga.handler.generic.Repositories;

public class GrepHandlerTest {

    @Test
    public void shouldReturnGrepResponseLines() {
        // given
        GrepResponseLine matchingResponseLine =
                new GrepResponseLine("myRepository", "Hello.java", 5, "public class Hello {");

        DvcsRepository matchingRepository = should(DvcsRepository.class)
                .returnDirectoryName("myRepository")
                .returnGrepResponse(asList(matchingResponseLine))
                .get();

        GrepResponseLine otherResponseLine =
                new GrepResponseLine("otherRepository", "Bye.java", 3, "public class Bye {");

        DvcsRepository otherRepository = should(DvcsRepository.class)
                .returnDirectoryName("otherRepository")
                .returnGrepResponse(asList(otherResponseLine))
                .get();

        Repositories repositories = mock(Repositories.class);
        when(repositories.getRepositories())
                .thenReturn(asList(matchingRepository, otherRepository));

        GrepHandler grepHandler = new GrepHandler(repositories);

        GrepRequest request = new GrepRequest.Builder("class", asList("myRepository")).ignoreCase(true).build();

        // when
        GrepResponse response = grepHandler.grep(request);

        // then
        assertThat(response).hasOnlyGrepResponseLines(matchingResponseLine);
    }

}
