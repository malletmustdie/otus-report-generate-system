#!/bin/bash

PROJECT_NAME=umbrella
NAMESPACE=dev

# Установка Kafka
helm install kafka oci://registry-1.docker.io/bitnamicharts/kafka -f ./${PROJECT_NAME}-chart/kafka-values.yaml --namespace ${NAMESPACE}

# Установка Kafka-ui
helm repo add kafka-ui https://provectus.github.io/kafka-ui-charts
helm install kafka-ui kafka-ui/kafka-ui -f ./${PROJECT_NAME}-chart/kafka-ui-values.yaml --namespace ${NAMESPACE}