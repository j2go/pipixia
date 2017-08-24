#!/usr/bin/env bash

docker run -v /root/pipixia-0.0.2-SNAPSHOT.jar:/app/pipixia.jar -p 8800:8080 --name pipi -ti java:8-alpine sh