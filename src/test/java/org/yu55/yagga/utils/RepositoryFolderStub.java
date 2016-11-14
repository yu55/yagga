package org.yu55.yagga.utils;

import static org.yu55.yagga.impl.git.GitRepositoryResolver.GIT_REPOSITORY_DISCRIMINATOR;
import static org.yu55.yagga.impl.mercurial.MercurialRepositoryResolver.MERCURIAL_REPOSITORY_DISCRIMINATOR;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.UUID;

/**
 * Allows to create temporary directory that is treated as potential repository. Note that created directory
 * physically exists in the file system so consider executing {@link #destroyRepository()} method when
 * <ff>RepositoryFolderStub</ff> won't be needed anymore.
 */
public class RepositoryFolderStub {

    private Path repositoryPath;

    private RepositoryFolderStub() {
        try {
            repositoryPath = Files.createTempDirectory(UUID.randomUUID().toString());
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    public static RepositoryFolderStub stubUndefinedRepository() {
        return new RepositoryFolderStub();
    }

    public static RepositoryFolderStub stubGitRepository() {
        return stubUndefinedRepository().containingDirectory(GIT_REPOSITORY_DISCRIMINATOR);
    }

    public static RepositoryFolderStub stubMercurialRepository() {
        return stubUndefinedRepository().containingDirectory(MERCURIAL_REPOSITORY_DISCRIMINATOR);
    }

    public RepositoryFolderStub containingDirectory(String directoryName) {
        try {
            Files.createDirectory(repositoryPath.resolve(directoryName));
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
        return this;
    }

    public RepositoryFolderStub containingFile(String fileName) {
        try {
            Files.createFile(repositoryPath.resolve(fileName));
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
        return this;
    }

    public Path getPath() {
        return repositoryPath;
    }

    public void destroyRepository() {
        try {
            Files.walkFileTree(repositoryPath, new SimpleFileVisitor<Path>() {

                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            Files.delete(dir);
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            Files.delete(file);
                            return FileVisitResult.CONTINUE;
                        }
                    }
            );
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

}
