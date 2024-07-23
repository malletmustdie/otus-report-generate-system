FROM maven:3.9.6-amazoncorretto-21 AS builder
COPY pom.xml /tmp/
COPY src /tmp/src
WORKDIR /tmp
RUN mvn clean package

FROM amazoncorretto:21-alpine3.19
COPY --from=builder /tmp/target/app.jar /tmp/app.jar
WORKDIR /tmp
EXPOSE 80
RUN ls -R /tmp
CMD ls -R /tmp && \
java -jar /tmp/app.jar \
--spring.config.location=/tmp/conf/application.yml \
--spring.config.additional-location=/tmp/secret/secret.yml