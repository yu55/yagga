package org.yu55.yagga.handler.mercurial;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yu55.yagga.handler.api.DvcsRepository;
import org.yu55.yagga.handler.api.DvcsRepositoryDescriptor;
import org.yu55.yagga.handler.mercurial.command.common.MercurialCommandExecutorFactory;

@Component
public class MercurialRepositoryDescriptor implements DvcsRepositoryDescriptor {

    public static final String MERCURIAL_REPOSITORY_DISCRIMINATOR = ".hg";

    private MercurialCommandExecutorFactory mercurialCommandExecutorFactory;

    @Autowired
    MercurialRepositoryDescriptor(MercurialCommandExecutorFactory mercurialCommandExecutorFactory) {
        this.mercurialCommandExecutorFactory = mercurialCommandExecutorFactory;
    }

    @Override
    public boolean isRepository(Path directory) {
        return Files.isDirectory(directory.resolve(MERCURIAL_REPOSITORY_DISCRIMINATOR));
    }

    @Override
    public DvcsRepository createRepository(Path directory) {
        return new MercurialRepository(directory, mercurialCommandExecutorFactory);
    }

}
