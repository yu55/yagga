package org.yu55.yagga.impl.git;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.yu55.yagga.common.repository.RepositoriesContainer;
import org.yu55.yagga.impl.git.command.common.GitCommandExecutorFactory;

import com.atlassian.stash.rest.client.api.StashClient;
import com.atlassian.stash.rest.client.api.entity.Page;
import com.atlassian.stash.rest.client.api.entity.Project;
import com.atlassian.stash.rest.client.api.entity.Repository;
import com.atlassian.stash.rest.client.httpclient.HttpClientConfig;
import com.atlassian.stash.rest.client.httpclient.HttpClientStashClientFactory;
import com.atlassian.stash.rest.client.httpclient.HttpClientStashClientFactoryImpl;

@Component
@ConditionalOnProperty(name = "yagga.stash.url" )
public class StashSync {

    private static final Logger logger = LoggerFactory.getLogger(StashSync.class);

    private static final int PAGE_MAX_SIZE = 10000;

    @Value("${yagga.repositories.paths}")
    private String[] pathsToRepositories;

    @Value("${yagga.stash.url}")
    private String stashUrl;

    @Value("${yagga.stash.localRepoPath}")
    private String stashLocalRepoPath;

    @Value("${yagga.stash.username}")
    private String username;

    @Value("${yagga.stash.password}")
    private String password;

    private RepositoriesContainer repositoriesContainer;

    private GitCommandExecutorFactory gitCommandExecutorFactory;

    @Autowired
    public StashSync(RepositoriesContainer repositoriesContainer, GitCommandExecutorFactory gitCommandExecutorFactory) {
        this.repositoriesContainer = repositoriesContainer;
        this.gitCommandExecutorFactory = gitCommandExecutorFactory;
    }

    @PostConstruct
    public void postConstruct() {
        stashLocalRepoPathValidation();
    }

    public void stashLocalRepoPathValidation() {
        if(!Arrays.asList(pathsToRepositories).contains(stashLocalRepoPath)) {
            throw new StashSyncException(stashLocalRepoPath + " not in " + Arrays.toString(pathsToRepositories));
        }
    }

    public void synchronizeWithStash() {
        try {
            logger.info("Starting synchronizing with stash: {}", stashUrl);
            StashClient client = initializeStashClient();
            Page<Project> projectPage = client.getAccessibleProjects(0, PAGE_MAX_SIZE);
            projectPage.getValues().forEach(project -> syncProject(project, client));
            logger.info("Stash synchronization completed");
        } catch (MalformedURLException ex) {
            logger.error("Can't sync with stash", ex);
        }
    }

    private StashClient initializeStashClient() throws MalformedURLException {
        URL baseUrl = new URL(stashUrl);
        HttpClientConfig clientConfig = new HttpClientConfig(baseUrl, username,
                new String(Base64.getDecoder().decode(password)));
        HttpClientStashClientFactory factory = new HttpClientStashClientFactoryImpl();
        return factory.getStashClient(clientConfig);
    }

    private void syncProject(Project project, StashClient client) {
        final List<Repository> stashReps = client.getRepositories(project.getKey(), null, 0, PAGE_MAX_SIZE).getValues();
        for (Repository stashRepo : stashReps) {
            final String repositoryEndingPath = stashRepo.getProject().getName() + File.separator + stashRepo.getSlug();
            if (repositoriesContainer.getRepositoryEndedWith(Paths.get(repositoryEndingPath)).isPresent()) {
                logger.debug(repositoryEndingPath + " present on local machine: skipping");
            } else {
                syncMissingRepository(stashRepo, repositoryEndingPath);
            }
        }
    }

    private void syncMissingRepository(Repository stashRepo, String repositoryEndingPath) {
        logger.info(repositoryEndingPath + " not present on local machine: cloning...");
        final File repoSubDir = new File(
                stashLocalRepoPath + File.separator + stashRepo.getProject().getName() + File.separator);
        try {
            if (!repoSubDir.exists()) {
                Files.createDirectory(repoSubDir.toPath());
            }
            gitCommandExecutorFactory.factorizeClone(repoSubDir, stashRepo.getSshCloneUrl()).execute();
            repositoriesContainer.update();
            logger.info(repositoryEndingPath + " cloned");
        } catch (IOException e) {
            logger.error("Cannot create directory {}", repoSubDir, e);
        }
    }
}
