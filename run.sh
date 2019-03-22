#!/usr/bin/env bash

set -e
mvn clean package -U -Dmaven.test.skip=true
sudo docker-compose down
sudo docker-compose  up -d --build







