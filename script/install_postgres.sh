#!/bin/bash

NAMESPACE=dev
PG_USERNAME=postgres
PG_PASSWORD=postgres

# Установка PostgreSQL через Helm
helm install postgres oci://registry-1.docker.io/bitnamicharts/postgresql \
--set auth.username=${PG_USERNAME},auth.password=${PG_PASSWORD} \
--namespace ${NAMESPACE}

sleep 20

# Создать новые базы данных
PG_POD_NAME=$(kubectl get pods -l app.kubernetes.io/name=postgresql -n ${NAMESPACE} -o jsonpath='{.items[0].metadata.name}')
kubectl exec "${PG_POD_NAME}" -n ${NAMESPACE} \
        -- bash -c 'export PGPASSWORD=postgres \
                    && psql -U postgres -c "CREATE DATABASE external_data_service;" \
                    && psql -U postgres -c "CREATE DATABASE config_control_service;"'