package org.yu55.yagga.common.repository.scheduler;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.yu55.yagga.common.repository.RepositoriesContainer;
import org.yu55.yagga.common.repository.Repository;
import org.yu55.yagga.impl.git.StashSync;

@Component
@ConditionalOnProperty(name = "yagga.scheduler.updateRepositories.enabled", havingValue = "true")
public class RepositoriesUpdateScheduler {

    private static final Logger logger = LoggerFactory.getLogger(RepositoriesUpdateScheduler.class);

    private RepositoriesContainer repositoriesContainer;

    private StopWatch stopWatch;

    private Optional<StashSync> stashSync;

    @Autowired
    public RepositoriesUpdateScheduler(RepositoriesContainer repositoriesContainer, Optional<StashSync> stashSync) {
        this.repositoriesContainer = repositoriesContainer;
        this.stopWatch = new StopWatch();
        this.stashSync = stashSync;
    }

    @Scheduled(fixedRateString = "${yagga.scheduler.updateRepositories.intervalInMillis}")
    public void refreshRepositories() {

        stashSync.ifPresent(StashSync::synchronizeWithStash);

        logger.info("Updating repositories...");
        stopWatch.start();

        repositoriesContainer.getRepositories().forEach(Repository::refresh);

        stopWatch.stop();
        logger.info("Updated {} repositories in {} seconds", repositoriesContainer.getRepositories().size(),
                stopWatch.getLastTaskTimeMillis() / 1000.0);
    }

}
