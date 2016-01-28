package org.yu55.yagga.handler.generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.yu55.yagga.handler.api.DvcsRepository;

@Component
@ConditionalOnProperty(name = "yagga.scheduler.updateRepositories.enabled", havingValue = "true")
public class RepositoriesUpdateScheduler {

    private static final Logger logger = LoggerFactory.getLogger(RepositoriesUpdateScheduler.class);

    private Repositories repositories;

    private StopWatch stopWatch;

    @Autowired
    public RepositoriesUpdateScheduler(Repositories repositories) {
        this.repositories = repositories;
        this.stopWatch = new StopWatch();
    }

    @Scheduled(fixedRateString = "${yagga.scheduler.updateRepositories.intervalInMillis}")
    public void refreshRepositories() {
        logger.info("Updating repositories...");
        stopWatch.start();

        repositories.getRepositories().forEach(DvcsRepository::refresh);

        stopWatch.stop();
        logger.info("Updated {} repositories in {} seconds", repositories.getRepositories().size(),
                stopWatch.getLastTaskTimeMillis() / 1000.0);
    }
}
