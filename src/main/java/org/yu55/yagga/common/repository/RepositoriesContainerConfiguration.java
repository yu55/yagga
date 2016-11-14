package org.yu55.yagga.common.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoriesContainerConfiguration {

    @Bean
    @Autowired
    public RepositoriesContainer repositoriesContainer(
            @Value("${yagga.repositories.paths}") String[] pathsToRepositories,
            RepositoryFactory repositoryFactory) {
        return new RepositoriesContainer(new RepositoriesFileVisitor(pathsToRepositories, repositoryFactory));
    }
}
