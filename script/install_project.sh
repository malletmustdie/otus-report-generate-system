#!/bin/bash

PROJECT_NAME=umbrella
NAMESPACE=dev

cd ..

# Установка или обновление основного проекта
helm upgrade report-generate-service ./${PROJECT_NAME}-chart \
-n ${NAMESPACE} \
--install