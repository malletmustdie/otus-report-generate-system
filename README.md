# Сервис генерации отчетов
Система Генерации Отчетов предназначена для упрощения процесса создания и управления структурированными документами. Эта система разработана для автоматизации генерации отчетов, обеспечения их согласованности и повышения производительности.

## Содержание
- [Основные функции](#основные-функции)
- [Технологии](#технологии)
- [Архитектура](#архитектура)
- [Тестирование](#тестирование)
- [To do](#to-do)

## Основные функции
- **Автоматическая генерация отчетов**: Автоматически создавайте детализированные отчеты на основе входных данных.
- **Управление шаблонами**: Используйте и управляйте различными шаблонами для различных типов отчетов.
- **Настраиваемое содержание**: Легко настраивайте содержание отчетов в соответствии с требованиями.
- **Поддержка нескольких форматов**: Экспортируйте отчеты в различных форматах, включая PDF и DOCX.

## Технологии
- [Spring-boot](https://spring.io/projects/spring-boot/)
- [Java 17+](https://openjdk.org/projects/jdk/)
- [Jasper Reports](https://www.jaspersoft.com/products/jaspersoft-community)
- [Postgres](https://www.postgresql.org/)
- [MinIO](https://min.io/)
- [Keycloak](https://www.keycloak.org/)
- [Docker](https://www.docker.com/)
- [Kubernetes](https://kubernetes.io/)
- [Helm](https://helm.sh/)
- [Jaeger](https://www.jaegertracing.io/)
- [Prometheus](https://prometheus.io/)

## Архитектура
**Общая схема взаимодействия сервисов**:
![alt text](https://github.com/malletmustdie/otus-report-generate-system/blob/main/tech_docs/img/scheme.png)

## Использование
## **Для использования сервиса под свои нужды вы можете переопределять переменные в values umbrella чарта [здесь](https://github.com/malletmustdie/otus-report-generate-system/blob/main/umbrella-chart/values.yaml)**

### **Postgres**
В чартах указаны креды для подключения к базам данных, но при желании можно переопределить для своих примеров
```
config-control-service:
  database:
    host: postgres-postgresql
    port: 5432
    db: config_control_service
    username: <db-username>
    password: <db-password>
...
external-data-service:
  database:
    host: postgres-postgresql
    port: 5432
    db: external_data_service
    username: <db-username>
    password: <db-password>
```
### **Keycloak**
- После вызова `install_keycloak.sh` и деплоя потребуется добавить настройки realm, json можно найти [здесь](https://github.com/malletmustdie/otus-report-generate-system/blob/main/umbrella-chart/keycloak/realm-export.json)
- В консоли Keycloak выполните следующие действия `Keycloak console -> Create Realm -> Resource file`
В связи с тем, что api-gateway проксирует методы Keycloak admin-api так же потребуется сгенерировать и вставить в секрет `client_id` для `health` или вашего клиента и `admin-cli`
```
api-gateway:
  realm: health-realm
  token:
    client:
      clientId: <client>
      clientSecret: <client-secret>
    admin:
      clientId: admin-cli
      clientSecret: <client-secret>
```
### **Minio**
- После вызова `install_minio.sh` и деплоя потребуется добавить `access-key` и`secret-key` секрет и при желании `bucketname` 
```
report-data-service:
  minio:
    bucketName: <bucketname>
    accessKey: <access-key>
    secretKey: <secret-key>
```
### **SMS рассылка**
Для рассылки отчета по Email потребуется использование SMTP сервера, его настройки можно указать 
```
report-delivery-service:
  mail:
    host: <smtp-host>
    username: <smtp-username>
    password: <smtp-password>
    port: <smtp-port>
```

## Разработка

### Требования
Для установки и запуска проекта, необходимо наличие на локальной машине дистрибутивы:
- [Docker](https://www.docker.com/)
- [Kubernetes](https://kubernetes.io/): `Minikube` etc
- [Helm](https://helm.sh/)

### Установка зависимостей
Для установки зависимостей, выполните команду или воспользуйтесь скриптами [здесь](https://github.com/malletmustdie/otus-report-generate-system/blob/main/script):
```
helm-upgrade.sh
```

## Тестирование
Для прогона тест кейсов можно использовать `postman` коллекцию, взять которую можно [здесь](https://github.com/malletmustdie/otus-report-generate-system/blob/main/test-collection-postman)

## To do
- [x] Добавить крутое README
- [ ] Всё переписать