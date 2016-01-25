package org.yu55.yagga.handler.generic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yu55.yagga.handler.api.DvcsRepositoryFactory;

@Configuration
public class RepositoriesConfiguration {

    @Value("${yagga.repositories.paths}")
    private String[] pathsToRepositories;

    @Autowired
    private DvcsRepositoryFactory dvcsRepositoryFactory;

    @Bean
    public Repositories repositories() {
        return new Repositories(new RepositoriesFileVisitor(pathsToRepositories, dvcsRepositoryFactory));
    }
}
