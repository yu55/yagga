package com.github.yu55.gog.handler.git.command;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.ProcessDestroyer;
import org.junit.Test;
import org.mockito.Mockito;

public class GitCommandExecutorTest {

    @Test
    public void shouldExecuteCommand() {
        // given
        Executor executor = new ExecutorStub(
                "[/repo] test output line 1\n" +
                        "[/repo] test output line 2");
        GitCommandExecutor gitCommandExecutor = new GitCommandExecutor(Mockito.mock(GitCommand.class), executor);

        // when
        GitCommandOutput output = gitCommandExecutor.execute(new File("/repo"));

        // then
        assertThat(output.getOutputLines()
                .stream().map(GitCommandOutputLine::getLine).collect(Collectors.toList()))
                .containsExactly("[/repo] test output line 1", "[/repo] test output line 2");
    }

    class ExecutorStub implements Executor {

        private final String executorOutput;

        private ExecuteStreamHandler streamHandler;

        private File dir;

        public ExecutorStub(String executorOutput) {
            this.executorOutput = executorOutput;
        }

        @Override
        public void setExitValue(int value) {
        }

        @Override
        public void setExitValues(int[] values) {
        }

        @Override
        public boolean isFailure(int exitValue) {
            return false;
        }

        @Override
        public ExecuteStreamHandler getStreamHandler() {
            return null;
        }

        @Override
        public void setStreamHandler(ExecuteStreamHandler streamHandler) {
            this.streamHandler = streamHandler;
        }

        @Override
        public ExecuteWatchdog getWatchdog() {
            return null;
        }

        @Override
        public void setWatchdog(ExecuteWatchdog watchDog) {
        }

        @Override
        public ProcessDestroyer getProcessDestroyer() {
            return null;
        }

        @Override
        public void setProcessDestroyer(ProcessDestroyer processDestroyer) {
        }

        @Override
        public File getWorkingDirectory() {
            return dir;
        }

        @Override
        public void setWorkingDirectory(File dir) {
            this.dir = dir;
        }

        @Override
        public int execute(CommandLine command) throws IOException {
            streamHandler.setProcessOutputStream(
                    new ByteArrayInputStream(executorOutput.getBytes()));
            streamHandler.start();
            streamHandler.stop();
            return 0;
        }

        @Override
        public int execute(CommandLine command, Map<String, String> environment) throws ExecuteException, IOException {
            return 0;
        }

        @Override
        public void execute(CommandLine command, ExecuteResultHandler handler) throws ExecuteException, IOException {
        }

        @Override
        public void execute(CommandLine command, Map<String, String> environment,
                            ExecuteResultHandler handler) throws ExecuteException, IOException {
        }
    }
}