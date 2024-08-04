#!/bin/bash

# Название и версия образа
IMAGE_NAME="report-generator-service"
IMAGE_VERSION="0.1.4"
DOCKER_HUB_USERNAME="malletmustdie"

# Полное имя образа
FULL_IMAGE_NAME="${DOCKER_HUB_USERNAME}/${IMAGE_NAME}:${IMAGE_VERSION}"

# Сборка образа
echo "Building Docker image..."
docker build -t ${IMAGE_NAME}:${IMAGE_VERSION} .

# Тегирование образа
echo "Tagging Docker image..."
docker tag ${IMAGE_NAME}:${IMAGE_VERSION} ${FULL_IMAGE_NAME}

# Пуш образа в Docker Hub
echo "Pushing Docker image to registry..."
docker push ${FULL_IMAGE_NAME}

echo "Docker image ${FULL_IMAGE_NAME} has been pushed successfully."