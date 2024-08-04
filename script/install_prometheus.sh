#!/bin/bash

NAMESPACE=dev

cd ..

helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm install prometheus prometheus-community/kube-prometheus-stack -f ./umbrella-chart/prometheus/prometheus.yaml --namespace ${NAMESPACE}