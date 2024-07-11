#!/bin/bash

PROJECT_NAME=umbrella
NAMESPACE=dev

# Установка или обновление основного проекта
helm upgrade --install ${PROJECT_NAME} ./${PROJECT_NAME}-chart --namespace ${NAMESPACE}