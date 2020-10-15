FROM maven:3.6.3-jdk-11 AS MAVEN_BUILD

WORKDIR /customer/service

COPY . ./

RUN mvn clean package

FROM openjdk:11-jre-slim

COPY --from=MAVEN_BUILD /customer/service/target/customer-service-0.0.1-SNAPSHOT.jar /unbox/customer-service.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/unbox/customer-service.jar"]