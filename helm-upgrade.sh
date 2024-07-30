#!/bin/bash

chmod +x script/create_namespace.sh script/install_postgres.sh script/install_kafka.sh ./script/install_prometheus.sh ./script/install_keycloak.sh script/install_project.sh

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
#./script/install_prometheus.sh

### Установка keycloak
#./script/install_keycloak.sh

# Установка основного проекта
./script/install_project.sh