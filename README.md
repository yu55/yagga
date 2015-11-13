[![Build Status](https://travis-ci.org/yu55/yagga.svg?branch=master)](https://travis-ci.org/yu55/yagga)

# Yet Another Git Grep Application (yagga)

This is a simple web application that allows you to git grep over many repositories via web interface.

## Requirements
* Java 8
* globally recognizable `git` command in operating system (added to $PATH etc.)

## Installation & running
* clone this repository to your local machine
* to build execute: `./gradlew clean build` in `yagga/` main directory
* fill the `yagga/yagga-config.properties` file with your repositories directories
* to run execute `java -Dserver.port=8080 -jar yagga-x.y.z-SNAPSHOT.jar --spring.config.location=/home/user/yagga/yagga-config.properties` in `yagga/build/libs/` directory
* open page `localhost:8080` and start searching your repositories
