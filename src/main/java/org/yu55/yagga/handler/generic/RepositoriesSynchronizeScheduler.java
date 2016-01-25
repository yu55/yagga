package org.yu55.yagga.handler.generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@ConditionalOnProperty(name = "yagga.scheduler.detectDirectoriesChange.enabled", havingValue = "true")
public class RepositoriesSynchronizeScheduler {
    private static final Logger logger = LoggerFactory.getLogger(RepositoriesSynchronizeScheduler.class);

    private Repositories repositories;

    private StopWatch stopWatch;

    @Autowired
    public RepositoriesSynchronizeScheduler(Repositories repositories) {
        this.repositories = repositories;
        this.stopWatch = new StopWatch();
    }


    @Scheduled(fixedRateString = "${yagga.scheduler.detectDirectoriesChange.intervalInMillis}")
    public void synchronizeDirectories() {
        logger.info("Synchronizing directories...");
        stopWatch.start();

        repositories.update();

        stopWatch.stop();
        logger.info("Synchronized directories in {} seconds", stopWatch.getLastTaskTimeMillis() / 1000.0);
    }
}
