package org.yu55.yagga.impl.generic;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.yu55.yagga.common.repository.RepositoriesContainer;
import org.yu55.yagga.common.repository.RepositoriesFileVisitor;
import org.yu55.yagga.common.repository.Repository;

public class RepositoriesContainerTest {

    @Test
    public void shouldUpdateOnConstruction() {

        // given
        Repository existingRepository = mock(Repository.class);
        Repository newlyCreatedRepository = mock(Repository.class);
        List<Repository> repositoryList1 = new ArrayList<>();
        repositoryList1.add(existingRepository);
        List<Repository> repositoryList2 = new ArrayList<>();
        repositoryList2.add(newlyCreatedRepository);

        RepositoriesFileVisitor repositoriesFileVisitor = mock(RepositoriesFileVisitor.class);
        when(repositoriesFileVisitor.getRepositories()).thenReturn(repositoryList1, repositoryList2);

        RepositoriesContainer repositoriesContainer = new RepositoriesContainer(repositoriesFileVisitor);

        // when
        List<Repository> result = repositoriesContainer.getRepositories();

        // then
        verify(repositoriesFileVisitor).getRepositories();
        assertThat(result).contains(existingRepository);
        assertThat(result).doesNotContain(newlyCreatedRepository);
    }


    @Test
    public void shouldReturnNewRepository() {

        // given
        Repository newlyCreatedRepository = mock(Repository.class);
        List<Repository> repositoryList1 = new ArrayList<>();
        List<Repository> repositoryList2 = new ArrayList<>();
        repositoryList2.add(newlyCreatedRepository);

        RepositoriesFileVisitor repositoriesFileVisitor = mock(RepositoriesFileVisitor.class);
        when(repositoriesFileVisitor.getRepositories()).thenReturn(repositoryList1, repositoryList2);

        RepositoriesContainer repositoriesContainer = new RepositoriesContainer(repositoriesFileVisitor);

        // when
        repositoriesContainer.update();
        List<Repository> result = repositoriesContainer.getRepositories();

        // then
        verify(repositoriesFileVisitor, times(2)).getRepositories();
        assertThat(result).contains(newlyCreatedRepository);
    }

    @Test
    public void shouldNotReturnDeletedRepository() {

        // given
        Repository removedRepository = mock(Repository.class);
        List<Repository> repositoryList1 = new ArrayList<>();
        repositoryList1.add(removedRepository);
        List<Repository> repositoryList2 = new ArrayList<>();

        RepositoriesFileVisitor repositoriesFileVisitor = mock(RepositoriesFileVisitor.class);
        when(repositoriesFileVisitor.getRepositories()).thenReturn(repositoryList1, repositoryList2);

        RepositoriesContainer repositoriesContainer = new RepositoriesContainer(repositoriesFileVisitor);

        // when
        repositoriesContainer.update();
        List<Repository> result = repositoriesContainer.getRepositories();

        // then
        verify(repositoriesFileVisitor, times(2)).getRepositories();
        assertThat(result).doesNotContain(removedRepository);
    }

}