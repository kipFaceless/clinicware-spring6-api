FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
COPY target/clinicware-0.0.1-SNAPSHOT.jar clinicware.jar
ENTRYPOINT ["java","-jar","/clinicware.jar"]
