package org.yu55.yagga.handler.git.command.common;

import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.stereotype.Component;
import org.yu55.yagga.handler.api.command.annotate.AnnotateParameters;
import org.yu55.yagga.handler.api.command.grep.GrepParameters;
import org.yu55.yagga.handler.git.GitRepository;
import org.yu55.yagga.handler.git.command.annotate.GitAnnotateCommand;
import org.yu55.yagga.handler.git.command.grep.GitGrepCommand;
import org.yu55.yagga.handler.git.command.pull.GitPullCommand;

@Component
public class GitCommandExecutorFactory {

    public GitCommandExecutor factorize(GitRepository gitRepository, GitCommand command) {
        DefaultExecutor executor = new DefaultExecutor();
        executor.setWorkingDirectory(gitRepository.getDirectory());
        GitCommandExecutorStreamHandler executorStreamHandler =
                new GitCommandExecutorStreamHandler(gitRepository.getDirectoryName());
        executor.setStreamHandler(new PumpStreamHandler(executorStreamHandler));
        return new GitCommandExecutor(command, executor, () -> executorStreamHandler.getOutput());
    }

    public GitCommandExecutor factorizePull(GitRepository repository) {
        return factorize(repository, new GitPullCommand());
    }

    public GitCommandExecutor factorizeAnnotate(GitRepository repository, AnnotateParameters annotateParameters) {
        return factorize(repository, new GitAnnotateCommand(annotateParameters));
    }

    public GitCommandExecutor factorizeGrep(GitRepository repository,
                                            GrepParameters grepParameters) {
        return factorize(repository, new GitGrepCommand(grepParameters));
    }
}
