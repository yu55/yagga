package org.yu55.yagga.impl.generic;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.yu55.yagga.common.repository.RepositoriesContainer;
import org.yu55.yagga.common.repository.scheduler.RepositoriesUpdateScheduler;
import org.yu55.yagga.common.repository.Repository;
import org.yu55.yagga.impl.git.StashSync;

public class RepositoriesContainerUpdateSchedulerTest {

    @Test
    public void testRefresh()  {
        // given
        Repository repository = mock(Repository.class);
        RepositoriesContainer repositoriesContainer = mock(RepositoriesContainer.class);
        Optional<StashSync> stashSync = Optional.empty();
        when(repositoriesContainer.getRepositories()).thenReturn(singletonList(repository));
        RepositoriesUpdateScheduler repositoriesUpdateScheduler = new RepositoriesUpdateScheduler(repositoriesContainer, stashSync);

        // when
        repositoriesUpdateScheduler.refreshRepositories();

        // then
        verify(repository).refresh();
    }
}
