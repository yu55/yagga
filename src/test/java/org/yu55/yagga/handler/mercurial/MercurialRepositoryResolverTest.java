package org.yu55.yagga.handler.mercurial;

import static org.assertj.core.api.Assertions.assertThat;
import static org.yu55.yagga.handler.mercurial.MercurialRepositoryResolver.MERCURIAL_REPOSITORY_DISCRIMINATOR;
import static org.yu55.yagga.testutils.RepositoryFolderStub.stubMercurialRepository;
import static org.yu55.yagga.testutils.RepositoryFolderStub.stubUndefinedRepository;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.yu55.yagga.handler.api.DvcsRepository;
import org.yu55.yagga.handler.mercurial.command.common.MercurialCommandExecutorFactory;
import org.yu55.yagga.testutils.RepositoryFolderStub;

@RunWith(MockitoJUnitRunner.class)
public class MercurialRepositoryResolverTest {

    @Mock
    private MercurialCommandExecutorFactory mercurialCommandExecutorFactory;

    private MercurialRepositoryResolver mercurialRepositoryResolver;

    private RepositoryFolderStub repositoryFolderStub;

    @Before
    public void setUp() {
        mercurialRepositoryResolver = new MercurialRepositoryResolver(mercurialCommandExecutorFactory);
    }

    @After
    public void tearDown() {
        if (repositoryFolderStub != null) {
            repositoryFolderStub.destroyRepository();
        }
    }

    @Test
    public void shouldRecognizeDirectoryAsMercurialRepository() {
        // given
        repositoryFolderStub = stubMercurialRepository();

        // when
        boolean isMercurial = mercurialRepositoryResolver.isRepository(repositoryFolderStub.getPath());

        // then
        assertThat(isMercurial).isTrue();
    }

    @Test
    public void shouldNotRecognizeDirectoryAsMercurialRepositoryWhenRepoDiscriminatorIsNotDirectory() {
        // given
        repositoryFolderStub = stubUndefinedRepository().containingFile(MERCURIAL_REPOSITORY_DISCRIMINATOR);

        // when
        boolean isMercurial = mercurialRepositoryResolver.isRepository(repositoryFolderStub.getPath());

        // then
        assertThat(isMercurial).isFalse();
    }

    @Test
    public void shouldNotRecognizeDirectoryAsMercurialRepositoryWhenRepoDiscriminatorDoesNotExist() {
        // given
        repositoryFolderStub = stubUndefinedRepository();

        // when
        boolean isMercurial = mercurialRepositoryResolver.isRepository(repositoryFolderStub.getPath());

        // then
        assertThat(isMercurial).isFalse();
    }

    @Test
    public void shouldNotRecognizeNotExistingDirectoryAsMercurialRepository() {
        // given
        Path repositoryPath = Paths.get("not_existing_path");

        // when
        boolean isMercurial = mercurialRepositoryResolver.isRepository(repositoryPath);

        // then
        assertThat(isMercurial).isFalse();
    }

    @Test
    public void shouldCorrectlyCreateGitRepositoryInstance() {
        //given
        Path repositoryPath = Paths.get("my_mercurial_repository");

        //when
        DvcsRepository repository = mercurialRepositoryResolver.resolveRepository(repositoryPath);

        //then
        assertThat(repository)
                .isExactlyInstanceOf(MercurialRepository.class)
                .matches(dvcsRepository -> dvcsRepository.isDirectoryNameEqual(repositoryPath.toString()));
    }

}
