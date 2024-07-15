#!/bin/bash

#!/bin/bash

NAMESPACE=dev
REGISTRY_NAME=registry-1.docker.io
REPOSITORY_NAME=bitnamicharts

helm install minio oci://$REGISTRY_NAME/$REPOSITORY_NAME/minio --namespace $NAMESPACE