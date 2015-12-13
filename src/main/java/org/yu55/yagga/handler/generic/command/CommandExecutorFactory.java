package org.yu55.yagga.handler.generic.command;

import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.stereotype.Component;
import org.yu55.yagga.handler.api.DvcsRepository;

@Component
public class CommandExecutorFactory {

    public CommandExecutor factorize(DvcsRepository repository, Command command) {
        DefaultExecutor executor = new DefaultExecutor();
        executor.setWorkingDirectory(repository.getDirectory().toFile());
        CommandExecutorStreamHandler executorStreamHandler =
                new CommandExecutorStreamHandler(repository.getDirectoryName());
        executor.setStreamHandler(new PumpStreamHandler(executorStreamHandler));
        return new CommandExecutor(command, executor, executorStreamHandler::getOutput);
    }

    public CommandExecutor factorize(Command command) {
        DefaultExecutor executor = new DefaultExecutor();
        CommandExecutorStreamHandler executorStreamHandler =
                new CommandExecutorStreamHandler(executor.getWorkingDirectory().getPath());
        executor.setStreamHandler(new PumpStreamHandler(executorStreamHandler));
        return new CommandExecutor(command, executor, executorStreamHandler::getOutput);
    }

}
