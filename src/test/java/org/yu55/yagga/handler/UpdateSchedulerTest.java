package org.yu55.yagga.handler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.yu55.yagga.handler.git.GitRepository;

public class UpdateSchedulerTest {

    @Test
    public void testPull() throws Exception {
        // given
        GitRepository repository = mock(GitRepository.class);
        Repositories repositories = mock(Repositories.class);
        when(repositories.getRepositories()).thenReturn(Arrays.asList(repository));
        UpdateScheduler pullScheduler = new UpdateScheduler(repositories);

        // when
        pullScheduler.pull();

        // then
        verify(repository).pull();
    }
}
