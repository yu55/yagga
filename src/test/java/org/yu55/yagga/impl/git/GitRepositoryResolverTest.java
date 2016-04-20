package org.yu55.yagga.impl.git;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.yu55.yagga.impl.git.GitRepositoryResolver.GIT_REPOSITORY_DISCRIMINATOR;
import static org.yu55.yagga.utils.RepositoryFolderStub.stubGitRepository;
import static org.yu55.yagga.utils.RepositoryFolderStub.stubUndefinedRepository;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.yu55.yagga.common.repository.Repository;
import org.yu55.yagga.common.command.CommandExecutor;
import org.yu55.yagga.common.command.CommandOutput;
import org.yu55.yagga.common.command.CommandOutputLine;
import org.yu55.yagga.impl.git.command.common.GitCommandExecutorFactory;
import org.yu55.yagga.utils.RepositoryFolderStub;

@RunWith(MockitoJUnitRunner.class)
public class GitRepositoryResolverTest {

    @Mock
    private GitCommandExecutorFactory gitCommandExecutorFactory;

    private GitRepositoryResolver gitRepositoryResolver;

    private RepositoryFolderStub repositoryFolderStub;

    @Before
    public void setUp() {
        gitRepositoryResolver = new GitRepositoryResolver(gitCommandExecutorFactory);
    }

    @After
    public void tearDown() {
        if (repositoryFolderStub != null) {
            repositoryFolderStub.destroyRepository();
        }
    }

    @Test
    public void shouldDetermineThatGitIsSupported() {
        // given
        CommandExecutor commandExecutor = commandExecutorReturningGitVersionOutput("git version 2.5.0");
        when(gitCommandExecutorFactory.factorizeVersion()).thenReturn(commandExecutor);

        // when
        gitRepositoryResolver.checkAvailability();
        boolean supported = gitRepositoryResolver.isSupported();

        // then
        assertThat(supported).isTrue();
    }

    @Test
    public void shouldDetermineThatGitIsNotSupported() {
        // given
        CommandExecutor commandExecutor = commandExecutorReturningGitVersionOutput("git version 2.2.0");
        when(gitCommandExecutorFactory.factorizeVersion()).thenReturn(commandExecutor);

        // when
        gitRepositoryResolver.checkAvailability();
        boolean supported = gitRepositoryResolver.isSupported();

        // then
        assertThat(supported).isFalse();
    }

    @Test
    public void shouldRecognizeDirectoryAsGitRepository() {
        // given
        repositoryFolderStub = stubGitRepository();

        // when
        boolean isGit = gitRepositoryResolver.isRepository(repositoryFolderStub.getPath());

        // then
        assertThat(isGit).isTrue();
    }

    @Test
    public void shouldNotRecognizeDirectoryAsGitRepositoryWhenRepoDiscriminatorIsNotDirectory() {
        // given
        repositoryFolderStub = stubUndefinedRepository().containingFile(GIT_REPOSITORY_DISCRIMINATOR);

        // when
        boolean isGit = gitRepositoryResolver.isRepository(repositoryFolderStub.getPath());

        // then
        assertThat(isGit).isFalse();
    }

    @Test
    public void shouldNotRecognizeDirectoryAsGitRepositoryWhenRepoDiscriminatorDoesNotExist() {
        // given
        repositoryFolderStub = stubUndefinedRepository();

        // when
        boolean isGit = gitRepositoryResolver.isRepository(repositoryFolderStub.getPath());

        // then
        assertThat(isGit).isFalse();
    }

    @Test
    public void shouldNotRecognizeNotExistingDirectoryAsGitRepository() {
        // given
        Path repositoryPath = Paths.get("not_existing_path");

        // when
        boolean isGit = gitRepositoryResolver.isRepository(repositoryPath);

        // then
        assertThat(isGit).isFalse();
    }

    @Test
    public void shouldCorrectlyCreateGitRepositoryInstance() {
        //given
        Path repositoryPath = Paths.get("my_git_repository");

        //when
        Repository repository = gitRepositoryResolver.resolveRepository(repositoryPath);

        //then
        assertThat(repository)
                .isExactlyInstanceOf(GitRepository.class)
                .matches(repo -> repo.isDirectoryNameEqual(repositoryPath.toString()));
    }

    private CommandExecutor commandExecutorReturningGitVersionOutput(String line) {
        CommandOutput commandOutput = new CommandOutput().addOutputLine(new CommandOutputLine(line));
        CommandExecutor commandExecutor = mock(CommandExecutor.class);
        when(commandExecutor.execute()).thenReturn(commandOutput);
        return commandExecutor;
    }

}
