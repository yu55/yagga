package org.yu55.yagga.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.yu55.yagga.handler.git.GitRepository;

@Component
public class UpdateScheduler {

    private static final Logger logger = LoggerFactory.getLogger(UpdateScheduler.class);

    private Repositories repositories;

    @Autowired
    public UpdateScheduler(Repositories repositories) {
        this.repositories = repositories;
    }

    @Scheduled(fixedRateString = "${repositories.refreshRateInMiliseconds}")
    public void pull() {
        logger.info("Pulling repositories...");
        repositories.getRepositories().forEach(GitRepository::pull);
        logger.info("Pulling repositories finished");
    }
}
