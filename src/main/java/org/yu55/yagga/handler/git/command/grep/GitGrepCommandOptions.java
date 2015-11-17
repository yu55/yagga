package org.yu55.yagga.handler.git.command.grep;

import org.yu55.yagga.core.grep.model.GrepRequest;

public class GitGrepCommandOptions {

    private boolean ignoreCase;

    public static GitGrepCommandOptions fromGitGrepCommandOptions(GrepRequest grepRequest) {
        GitGrepCommandOptions gitGrepCommandOptions = new GitGrepCommandOptions();
        gitGrepCommandOptions.setIgnoreCase(grepRequest.isIgnoreCase());
        return gitGrepCommandOptions;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }


}
