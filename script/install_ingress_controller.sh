#!/bin/bash

NAMESPACE=dev

cd ..

helm upgrade --install ingress-nginx ingress-nginx \
  --repo https://kubernetes.github.io/ingress-nginx \
  --namespace ${NAMESPACE}