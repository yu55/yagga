package org.yu55.yagga.handler.git;

import static org.assertj.core.api.Assertions.assertThat;
import static org.yu55.yagga.handler.git.GitRepositoryDescriptor.GIT_REPOSITORY_DISCRIMINATOR;
import static org.yu55.yagga.util.RepositoryFolderStub.stubGitRepository;
import static org.yu55.yagga.util.RepositoryFolderStub.stubUndefinedRepository;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.yu55.yagga.handler.api.DvcsRepository;
import org.yu55.yagga.handler.git.command.common.GitCommandExecutorFactory;
import org.yu55.yagga.util.RepositoryFolderStub;

@RunWith(MockitoJUnitRunner.class)
public class GitRepositoryDescriptorTest {

    @Mock
    private GitCommandExecutorFactory gitCommandExecutorFactory;

    private GitRepositoryDescriptor gitRepositoryDescriptor;

    private RepositoryFolderStub repositoryFolderStub;

    @Before
    public void setUp() {
        gitRepositoryDescriptor = new GitRepositoryDescriptor(gitCommandExecutorFactory);
    }

    @After
    public void tearDown() {
        if (repositoryFolderStub != null) {
            repositoryFolderStub.destroyRepository();
        }
    }

    @Test
    public void shouldRecognizeDirectoryAsGitRepository() {
        // given
        repositoryFolderStub = stubGitRepository();

        // when
        boolean isGit = gitRepositoryDescriptor.isRepository(repositoryFolderStub.getPath());

        // then
        assertThat(isGit).isTrue();
    }

    @Test
    public void shouldNotRecognizeDirectoryAsGitRepositoryWhenRepoDiscriminatorIsNotDirectory() {
        // given
        repositoryFolderStub = stubUndefinedRepository().containingFile(GIT_REPOSITORY_DISCRIMINATOR);

        // when
        boolean isGit = gitRepositoryDescriptor.isRepository(repositoryFolderStub.getPath());

        // then
        assertThat(isGit).isFalse();
    }

    @Test
    public void shouldNotRecognizeDirectoryAsGitRepositoryWhenRepoDiscriminatorDoesNotExist() {
        // given
        repositoryFolderStub = stubUndefinedRepository();

        // when
        boolean isGit = gitRepositoryDescriptor.isRepository(repositoryFolderStub.getPath());

        // then
        assertThat(isGit).isFalse();
    }

    @Test
    public void shouldNotRecognizeNotExistingDirectoryAsGitRepository() {
        // given
        Path repositoryPath = Paths.get("not_existing_path");

        // when
        boolean isGit = gitRepositoryDescriptor.isRepository(repositoryPath);

        // then
        assertThat(isGit).isFalse();
    }

    @Test
    public void shouldCorrectlyCreateGitRepositoryInstance() {
        //given
        Path repositoryPath = Paths.get("my_git_repository");

        //when
        DvcsRepository repository = gitRepositoryDescriptor.createRepository(repositoryPath);

        //then
        assertThat(repository)
                .isExactlyInstanceOf(GitRepository.class)
                .matches(dvcsRepository -> dvcsRepository.isDirectoryNameEqual(repositoryPath.toString()));
    }

}