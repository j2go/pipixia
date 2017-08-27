#!/usr/bin/env bash

./gradlew clean assemble

scp build/libs/pipixia-0.0.2-SNAPSHOT.jar vmali:/root/

ssh vmali "
docker stop pipi &&\
docker rm pipi \
"

#docker run -v /root/pipixia-0.0.2-SNAPSHOT.jar:/app/pipixia.jar -p 8800:8080 --name pipi -ti java:8-alpine sh