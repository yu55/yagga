package org.yu55.yagga.handler.generic;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.yu55.yagga.handler.api.DvcsRepository;

public class RepositoriesUpdateSchedulerTest {

    @Test
    public void testPull()  {
        // given
        DvcsRepository repository = mock(DvcsRepository.class);
        Repositories repositories = mock(Repositories.class);
        when(repositories.getRepositories()).thenReturn(singletonList(repository));
        RepositoriesUpdateScheduler repositoriesUpdateScheduler = new RepositoriesUpdateScheduler(repositories);

        // when
        repositoriesUpdateScheduler.refreshRepositories();

        // then
        verify(repository).pull();
    }
}
