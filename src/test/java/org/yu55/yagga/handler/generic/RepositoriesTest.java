package org.yu55.yagga.handler.generic;


import org.junit.Test;
import org.yu55.yagga.handler.api.DvcsRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RepositoriesTest {

    @Test
    public void shouldUpdateOnConstruction() {

        // given
        DvcsRepository existingRepository = mock(DvcsRepository.class);
        DvcsRepository newlyCreatedRepository = mock(DvcsRepository.class);
        List<DvcsRepository> repositoryList1 = new ArrayList<>();
        repositoryList1.add(existingRepository);
        List<DvcsRepository> repositoryList2 = new ArrayList<>();
        repositoryList2.add(newlyCreatedRepository);

        RepositoriesFileVisitor repositoriesFileVisitor = mock(RepositoriesFileVisitor.class);
        when(repositoriesFileVisitor.getRepositories()).thenReturn(repositoryList1, repositoryList2);

        Repositories repositories = new Repositories(repositoriesFileVisitor);

        // when
        List<DvcsRepository> result = repositories.getRepositories();

        // flush
        verify(repositoriesFileVisitor).getRepositories();
        assertThat(result).contains(existingRepository);
        assertThat(result).doesNotContain(newlyCreatedRepository);
    }


    @Test
    public void shouldReturnNewRepository() {

        // given
        DvcsRepository newlyCreatedRepository = mock(DvcsRepository.class);
        List<DvcsRepository> repositoryList1 = new ArrayList<>();
        List<DvcsRepository> repositoryList2 = new ArrayList<>();
        repositoryList2.add(newlyCreatedRepository);

        RepositoriesFileVisitor repositoriesFileVisitor = mock(RepositoriesFileVisitor.class);
        when(repositoriesFileVisitor.getRepositories()).thenReturn(repositoryList1, repositoryList2);

        Repositories repositories = new Repositories(repositoriesFileVisitor);

        // when
        repositories.update();
        List<DvcsRepository> result = repositories.getRepositories();

        // flush
        verify(repositoriesFileVisitor, times(2)).getRepositories();
        assertThat(result).contains(newlyCreatedRepository);
    }

    @Test
    public void shouldNotReturnDeletedRepository() {

        // given
        DvcsRepository removedRepository = mock(DvcsRepository.class);
        List<DvcsRepository> repositoryList1 = new ArrayList<>();
        repositoryList1.add(removedRepository);
        List<DvcsRepository> repositoryList2 = new ArrayList<>();

        RepositoriesFileVisitor repositoriesFileVisitor = mock(RepositoriesFileVisitor.class);
        when(repositoriesFileVisitor.getRepositories()).thenReturn(repositoryList1, repositoryList2);

        Repositories repositories = new Repositories(repositoriesFileVisitor);

        // when
        repositories.update();
        List<DvcsRepository> result = repositories.getRepositories();

        // flush
        verify(repositoriesFileVisitor, times(2)).getRepositories();
        assertThat(result).doesNotContain(removedRepository);
    }

}