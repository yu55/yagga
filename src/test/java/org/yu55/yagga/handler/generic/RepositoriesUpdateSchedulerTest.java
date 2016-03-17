package org.yu55.yagga.handler.generic;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.yu55.yagga.handler.api.DvcsRepository;
import org.yu55.yagga.handler.git.StashSync;

public class RepositoriesUpdateSchedulerTest {

    @Test
    public void testRefresh()  {
        // given
        DvcsRepository repository = mock(DvcsRepository.class);
        Repositories repositories = mock(Repositories.class);
        StashSync stashSync = mock(StashSync.class);
        when(repositories.getRepositories()).thenReturn(singletonList(repository));
        RepositoriesUpdateScheduler repositoriesUpdateScheduler = new RepositoriesUpdateScheduler(repositories, stashSync);

        // when
        repositoriesUpdateScheduler.refreshRepositories();

        // then
        verify(repository).refresh();
    }
}
