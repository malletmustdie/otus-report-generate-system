#!/bin/bash

PROJECT_NAME=umbrella
NAMESPACE=dev

cd ..

cd ${PROJECT_NAME}-chart

# Установка или обновление основного проекта
helm dep update