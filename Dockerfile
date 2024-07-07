#FROM ubuntu:latest AS build
#RUN apt-get update
#RUN apt-get install openjdk-17-jdk -y
#COPY . .

#RUN apt-get install maven -y
#RUN mvn clean install

#FROM openjdk:17-jdk-slim
#VOLUME /tmp
#EXPOSE 8080
#COPY --from=build /target/clinicware-0.0.1-SNAPSHOT.jar clinicware.jar
    #COPY target/clinicware-0.0.1-SNAPSHOT.jar clinicware.jar
#ENTRYPOINT ["java","-jar","/clinicware.jar"]


# Etapa de build
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

# Etapa de execução
FROM openjdk:17-jdk-slim
VOLUME /tmp
EXPOSE 8080
COPY --from=build /app/target/clinicware-0.0.1-SNAPSHOT.jar clinicware.jar
ENTRYPOINT ["java", "-jar", "/clinicware.jar"]

