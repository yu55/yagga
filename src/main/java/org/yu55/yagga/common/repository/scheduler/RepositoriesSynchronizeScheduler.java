package org.yu55.yagga.common.repository.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.yu55.yagga.common.repository.RepositoriesContainer;

@Component
@ConditionalOnProperty(name = "yagga.scheduler.detectDirectoriesChange.enabled", havingValue = "true")
public class RepositoriesSynchronizeScheduler {
    private static final Logger logger = LoggerFactory.getLogger(RepositoriesSynchronizeScheduler.class);

    private RepositoriesContainer repositoriesContainer;

    private StopWatch stopWatch;

    @Autowired
    public RepositoriesSynchronizeScheduler(RepositoriesContainer repositoriesContainer) {
        this.repositoriesContainer = repositoriesContainer;
        this.stopWatch = new StopWatch();
    }


    @Scheduled(fixedRateString = "${yagga.scheduler.detectDirectoriesChange.intervalInMillis}")
    public void synchronizeDirectories() {
        logger.info("Synchronizing directories...");
        stopWatch.start();

        repositoriesContainer.update();

        stopWatch.stop();
        logger.info("Synchronized directories in {} seconds", stopWatch.getLastTaskTimeMillis() / 1000.0);
    }
}
