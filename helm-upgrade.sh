#!/bin/bash

chmod +x script/create_namespace.sh script/install_postgres.sh script/install_kafka.sh ./script/install_prometheus.sh script/install_project.sh

## Создание namespace
#./deploy/create_namespace.sh
#
## Установка PostgreSQL
#./deploy/install_postgres.sh
##
### Установка Kafka
#./deploy/install_kafka.sh
#
### Установка prometheus
#./deploy/install_prometheus.sh

# Установка основного проекта
./script/install_project.sh