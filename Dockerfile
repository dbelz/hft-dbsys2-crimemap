FROM openjdk:8-alpine
RUN mkdir -p /opt/crimemap
WORKDIR /opt/crimemap
COPY target/crimemap-0.0.1-SNAPSHOT.jar /opt/crimemap
CMD ["java", "-jar", "crimemap-0.0.1-SNAPSHOT.jar"]
