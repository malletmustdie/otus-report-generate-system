#!/bin/bash

NAMESPACE=dev

# Перемещение в директорию, содержащую папку umbrella-chart
cd ..

cd umbrella-chart

# Установка keycloak-db и keycloak
##helm install keycloak-db bitnami/postgresql --values ./keycloak/keycloak-db-values.yaml --namespace ${NAMESPACE}
helm install keycloak codecentric/keycloakx --values ./keycloak/keycloak-server-values.yaml --namespace ${NAMESPACE}

