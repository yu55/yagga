package org.yu55.yagga.impl.mercurial;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.common.repository.Repository;
import org.yu55.yagga.common.repository.RepositoryResolver;
import org.yu55.yagga.impl.mercurial.command.common.MercurialCommandExecutorFactory;

@Component
public class MercurialRepositoryResolver implements RepositoryResolver {

    public static final String MERCURIAL_REPOSITORY_DISCRIMINATOR = ".hg";

    private MercurialCommandExecutorFactory mercurialCommandExecutorFactory;

    @Autowired
    MercurialRepositoryResolver(MercurialCommandExecutorFactory mercurialCommandExecutorFactory) {
        this.mercurialCommandExecutorFactory = mercurialCommandExecutorFactory;
    }

    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public boolean isRepository(Path directory) {
        return Files.isDirectory(directory.resolve(MERCURIAL_REPOSITORY_DISCRIMINATOR));
    }

    @Override
    public Repository resolveRepository(Path directory) {
        return new MercurialRepository(directory, mercurialCommandExecutorFactory);
    }

}
