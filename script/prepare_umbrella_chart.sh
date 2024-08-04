#!/bin/bash

PROJECT_NAME=umbrella

cd ..

cd ${PROJECT_NAME}-chart

# Установка или обновление основного проекта
helm dep update