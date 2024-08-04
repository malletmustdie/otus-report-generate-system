# Сервис генерации отчетов
Система Генерации Отчетов Otus предназначена для упрощения процесса создания и управления структурированными документами. Эта система разработана для автоматизации генерации отчетов, обеспечения их согласованности и повышения производительности.

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

### **Keycloak**
- После вызова `install_keycloak.sh` и деплоя потребуется добавить настройки realm, json можно найти [здесь](https://github.com/malletmustdie/otus-report-generate-system/blob/main/umbrella-chart/keycloak)
- В консоли Keycloak выполните следующие действия `Keycloak console -> Create Realm -> Resource file`
В связи с тем, что api-gateway проксирует методы Keycloak admin-api так же потребуется сгенерировать и вставить в секрет [api-gateway]() `client_id` для `health` и `admin-cli`
```
apiVersion: v1
kind: Secret
metadata:
  name: {{ .Chart.Name }}
stringData:
  secret.yml: |
    keycloak:
      token:
        client: health
        client-secret: health-secret
      admin:
        client: admin-cli
        client-secret: admin-cli-secret
```
### **Minio**
- После вызова `install_minio.sh` и деплоя потребуется добавить `secret-key` секрет [report-data-service]()
```
apiVersion: v1
kind: Secret
metadata:
  name: {{ .Chart.Name }}
stringData:
  secret.yml: |
    minio:
      access-key: admin
      secret-key: secret-key
```
### SMS рассылка
Для рассылки отчета по Email потребуется использование SMTP сервера, его настройки можно указать [здесь]()

## Разработка

### Требования
Для установки и запуска проекта, необходимо наличие на локальной машине дистрибутивы:
- [Docker](https://www.docker.com/)
- [Kubernetes](https://kubernetes.io/): `Minikube` etc
- [Helm](https://helm.sh/)

### Установка зависимостей
Для установки зависимостей, выполните команду:
```
helm-upgrade.sh
```

## Тестирование
Для прогона тест кейсов можно использовать `postman` коллекцию, взять которую можно [здесь]()

## To do
- [x] Добавить крутое README
- [ ] Всё переписать