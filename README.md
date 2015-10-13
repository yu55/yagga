# The Git Online Grep project

This is a simple web application that allows you to git grep over many repositories via web interface.

## Requirements
* Java 8
* globally recognizable `git` command in operating system (added to $PATH etc.)

## Installation & running
* clone this repository to your local machine
* to build execute: `./gradlew clean build` in `gog/` main directory
* fill the `gog/gog-config.properties` file with your repositories directories
* to run execute `java -Dserver.port=8080 -jar gog-x.y.z-SNAPSHOT.jar --spring.config.location=/home/user/gog/gog-config.properties` in `gog/build/libs/` directory
* open page `localhost:8080` and start searching your repositories
* please enjoy!
