[![Build Status](https://travis-ci.org/yu55/yagga.svg?branch=master)](https://travis-ci.org/yu55/yagga)

# Yagga (Yet Another Git Grep Application)

This is a simple standalone web application that allows you to grep over many git repositories via web interface.
To start working you just need to set a path to your repositories and run the application. Enjoy in a minute!

Note: **Yagga** also supports grep on mercurial repositories (not all functions available yet).

![yagga-main.png](/docs/screenshots/yagga-main.png?raw=true "Yagga main view")
![yagga-detail.png](/docs/screenshots/yagga-detail.png?raw=true "Yagga detailed view")

### Requirements
---

* Java 8
* globally recognizable `git` command in operating system (added to PATH variable etc.); git not older than version 2.5
* to mercurial users: same as above for `hg` command


### Installation & running
---

* clone repository/repositories to your local machine
* to build simply execute `./gradlew build`
* to run execute:
  * `./gradlew bootRun -Dyagga.repositories.paths=<comma_separated_paths_to_your_repositories>`, or
  * `java -jar yagga-<version>.jar --yagga.repositories.paths=<comma_separated_paths_to_your_repositories>`
  (by default **Yagga** jar file is placed at `./build/libs` directory).
* open page `http://localhost:8080` and start searching your repositories

##### Tip
To simplify running **Yagga** application you can take advantage of
[Spring relaxed binding](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html#boot-features-external-config-relaxed-binding)
and define system property that specifies repositories locations: `YAGGA_REPOSITORIES_PATHS=<comma_separated_paths_to_your_repositories>` (for example in .bashrc).
Than you can simply run **Yagga** by executing `./gradlew bootRun` or `java -jar yagga-<version>.jar`.


### Custom configuration
---

Property                                                   | Type    | Default value | Description
---------------------------------------------------------- |:-------:|:-------------:| -----------------------------------------------------------------------------------------------------------------------------
`yagga.repositories.paths`                                 | String  | \<undefined\> | comma-separated list of repositories paths, this is required property
`yagga.scheduler.updateRepositories.enabled`               | Boolean | false         | flag defining whether repositories defined with `yagga.repositories.paths` should be updated periodically
`yagga.scheduler.updateRepositories.intervalInMillis`      | Long    | 60000         | defines fixed period in milliseconds between the end of the last repositories update invocation and the beginning of the next
`yagga.scheduler.detectDirectoriesChange.enabled`          | Boolean | false         | flag defining if periodical check for repositories added or deleted from `yagga.repositories.paths` should take place
`yagga.scheduler.detectDirectoriesChange.intervalInMillis` | Long    | 60000         | defines fixed period in milliseconds between the end of the last directories check invocation and the beginning of the next
