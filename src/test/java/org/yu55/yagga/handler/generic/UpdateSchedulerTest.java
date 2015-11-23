package org.yu55.yagga.handler.generic;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.yu55.yagga.handler.api.DvcsRepository;

public class UpdateSchedulerTest {

    @Test
    public void testPull() throws Exception {
        // given
        DvcsRepository repository = mock(DvcsRepository.class);
        Repositories repositories = mock(Repositories.class);
        when(repositories.getRepositories()).thenReturn(Arrays.asList(repository));
        UpdateScheduler pullScheduler = new UpdateScheduler(repositories);

        // when
        pullScheduler.pull();

        // then
        verify(repository).pull();
    }
}
