package org.yu55.yagga.handler.git;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.yu55.yagga.handler.generic.Repositories;
import org.yu55.yagga.handler.git.command.common.GitCommandExecutorFactory;

import com.atlassian.stash.rest.client.api.StashClient;
import com.atlassian.stash.rest.client.api.entity.Page;
import com.atlassian.stash.rest.client.api.entity.Project;
import com.atlassian.stash.rest.client.api.entity.Repository;
import com.atlassian.stash.rest.client.httpclient.HttpClientConfig;
import com.atlassian.stash.rest.client.httpclient.HttpClientStashClientFactory;
import com.atlassian.stash.rest.client.httpclient.HttpClientStashClientFactoryImpl;

@Component
public class StashSync {

    private static final Logger logger = LoggerFactory.getLogger(StashSync.class);

    private static final int PAGE_MAX_SIZE = 1000;

    @Value("${yagga.repositories.paths:}")
    private String[] pathsToRepositories;

    @Value("${yagga.stash.url:}")
    private String stashUrl;

    @Value("${yagga.stash.username:}")
    private String username;

    @Value("${yagga.stash.password:}")
    private String password;

    private Repositories repositories;

    private GitCommandExecutorFactory gitCommandExecutorFactory;

    @Autowired
    public StashSync(Repositories repositories, GitCommandExecutorFactory gitCommandExecutorFactory) {
        this.repositories = repositories;
        this.gitCommandExecutorFactory = gitCommandExecutorFactory;
    }

    public void synchronizeWithStash() {
        try {
            if (StringUtils.isEmpty(stashUrl)) {
                logger.info("Stash URL not provided - won't sync with stash");
                return;
            }
            URL baseUrl = new URL(stashUrl);
            HttpClientConfig clientConfig = new HttpClientConfig(baseUrl, username,
                    new String(Base64.getDecoder().decode(password)));
            HttpClientStashClientFactory factory = new HttpClientStashClientFactoryImpl();
            StashClient client = factory.getStashClient(clientConfig);
            Page<Project> projectPage = client.getAccessibleProjects(0, PAGE_MAX_SIZE);
            projectPage.getValues().forEach(project -> syncProject(project, client));
        } catch (MalformedURLException ex) {
            logger.error("Can't sync with stash", ex);
        }
    }

    private void syncProject(Project project, StashClient client) {
        final List<Repository> stashRepos = client.getRepositories(project.getKey(), null, 0,
                PAGE_MAX_SIZE).getValues();
        for (Repository stashRepo : stashRepos) {
            final String repositoryEndingPath = stashRepo.getProject().getName() + File.separator + stashRepo.getName();
            if (repositories.getRepositoryEndedWith(Paths.get(repositoryEndingPath)).isPresent()) {
                logger.info(repositoryEndingPath + " present on local machine: skipping");
            } else {
                logger.info(repositoryEndingPath + " not present on local machine: cloning...");
                File repoSubDir = new File(
                        pathsToRepositories[0] + File.separator + stashRepo.getProject().getName() + File.separator);
                try {
                    if (!repoSubDir.exists()) {
                        Files.createDirectory(repoSubDir.toPath());
                    }
                    gitCommandExecutorFactory.factorizeClone(repoSubDir, stashRepo.getSshCloneUrl()).execute();
                    logger.info(repositoryEndingPath + " cloned");
                } catch (IOException e) {
                    logger.error("Cannot create directory {}", repoSubDir, e);
                }
            }
        }
    }
}
