FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY build/libs/cbr-rate-0.0.1-cbr-rate-plain.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app.jar"]