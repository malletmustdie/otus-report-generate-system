#!/bin/bash

NAMESPACE=dev

cd ..

#helm install prometheus prometheus-community/kube-prometheus-stack \
#      -f ./umbrella-chart/prometheus/prometheus.yaml \
#      --namespace ${NAMESPACE}

helm install prometheus-postgres-exporter prometheus-community/prometheus-postgres-exporter \
 -f ./umbrella-chart/prometheus/prometheus-postgres-exporter.yaml \
  --namespace ${NAMESPACE}