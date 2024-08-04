#!/bin/bash

NAMESPACE=dev

cd ..

#helm repo add jaeger-all-in-one https://raw.githubusercontent.com/hansehe/jaeger-all-in-one/master/helm/charts
#helm repo update
helm install jaeger jaeger-all-in-one/jaeger-all-in-one \
  -f ./umbrella-chart/jaeger/jaeger-values.yaml \
  --namespace ${NAMESPACE}