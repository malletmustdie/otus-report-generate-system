#!/bin/bash

chmod +x deploy/create_namespace.sh deploy/install_postgres.sh deploy/install_kafka.sh ./deploy/install_prometheus.sh deploy/install_project.sh

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
./deploy/install_project.sh