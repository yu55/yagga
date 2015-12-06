package org.yu55.yagga.handler.mercurial;

import static org.assertj.core.api.Assertions.assertThat;
import static org.yu55.yagga.handler.mercurial.MercurialRepositoryDescriptor.MERCURIAL_REPOSITORY_DISCRIMINATOR;
import static org.yu55.yagga.util.RepositoryFolderStub.stubMercurialRepository;
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
import org.yu55.yagga.handler.mercurial.command.common.MercurialCommandExecutorFactory;
import org.yu55.yagga.util.RepositoryFolderStub;

@RunWith(MockitoJUnitRunner.class)
public class MercurialRepositoryDescriptorTest {

    @Mock
    private MercurialCommandExecutorFactory mercurialCommandExecutorFactory;

    private MercurialRepositoryDescriptor mercurialRepositoryDescriptor;

    private RepositoryFolderStub repositoryFolderStub;

    @Before
    public void setUp() {
        mercurialRepositoryDescriptor = new MercurialRepositoryDescriptor(mercurialCommandExecutorFactory);
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
        boolean isMercurial = mercurialRepositoryDescriptor.isRepository(repositoryFolderStub.getPath());

        // then
        assertThat(isMercurial).isTrue();
    }

    @Test
    public void shouldNotRecognizeDirectoryAsMercurialRepositoryWhenRepoDiscriminatorIsNotDirectory() {
        // given
        repositoryFolderStub = stubUndefinedRepository().containingFile(MERCURIAL_REPOSITORY_DISCRIMINATOR);

        // when
        boolean isMercurial = mercurialRepositoryDescriptor.isRepository(repositoryFolderStub.getPath());

        // then
        assertThat(isMercurial).isFalse();
    }

    @Test
    public void shouldNotRecognizeDirectoryAsMercurialRepositoryWhenRepoDiscriminatorDoesNotExist() {
        // given
        repositoryFolderStub = stubUndefinedRepository();

        // when
        boolean isMercurial = mercurialRepositoryDescriptor.isRepository(repositoryFolderStub.getPath());

        // then
        assertThat(isMercurial).isFalse();
    }

    @Test
    public void shouldNotRecognizeNotExistingDirectoryAsMercurialRepository() {
        // given
        Path repositoryPath = Paths.get("not_existing_path");

        // when
        boolean isMercurial = mercurialRepositoryDescriptor.isRepository(repositoryPath);

        // then
        assertThat(isMercurial).isFalse();
    }

    @Test
    public void shouldCorrectlyCreateGitRepositoryInstance() {
        //given
        Path repositoryPath = Paths.get("my_mercurial_repository");

        //when
        DvcsRepository repository = mercurialRepositoryDescriptor.createRepository(repositoryPath);

        //then
        assertThat(repository)
                .isExactlyInstanceOf(MercurialRepository.class)
                .matches(dvcsRepository -> dvcsRepository.isDirectoryNameEqual(repositoryPath.toString()));
    }

}
