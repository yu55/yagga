package org.yu55.yagga.handler.git.command.common;

import org.apache.commons.exec.DefaultExecutor;
import org.springframework.stereotype.Component;
import org.yu55.yagga.handler.git.command.annotate.GitAnnotateCommand;
import org.yu55.yagga.handler.git.command.grep.GitGrepCommand;
import org.yu55.yagga.handler.git.command.pull.GitPullCommand;

@Component
public class GitCommandExecutorFactory {

    public GitCommandExecutor factorize(GitCommand command) {
        return new GitCommandExecutor(command, new DefaultExecutor());
    }

    public GitCommandExecutor factorizePull(){
        return factorize(new GitPullCommand());
    }

    public GitCommandExecutor factorizeAnnotate(String file) {
        return factorize(new GitAnnotateCommand(file));
    }

    public GitCommandExecutor factorizeGrep(String wanted) {
        return factorize(new GitGrepCommand(wanted));
    }
}
