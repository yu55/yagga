package org.yu55.yagga.handler.git;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;

public class GitPullSchedulerTest {

    @Test
    public void testPull() throws Exception {
        //given
        GitRepository repository = mock(GitRepository.class);
        GitRepositories repositories = mock(GitRepositories.class);
        when(repositories.getRepositories()).thenReturn(Arrays.asList(repository));
        GitPullScheduler pullScheduler = new GitPullScheduler(repositories);

        //when
        pullScheduler.pull();

        //then
        verify(repository).pull();
    }
}