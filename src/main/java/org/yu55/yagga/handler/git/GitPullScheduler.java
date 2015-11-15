package org.yu55.yagga.handler.git;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GitPullScheduler {

    private static final Logger logger = LoggerFactory.getLogger(GitPullScheduler.class);

    private GitRepositories gitRepositories;

    @Autowired
    public GitPullScheduler(GitRepositories gitRepositories) {
        this.gitRepositories = gitRepositories;
    }

    @Scheduled(fixedRateString = "${repositories.refreshRateInMilisecond}")
    public void pull() {
        logger.info("Pulling");
        gitRepositories.getRepositories().forEach(GitRepository::pull);
    }
}
