FROM openjdk:17-jdk-slim

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} cbr-rate.jar

ENTRYPOINT ["java", "-jar", "cbr-rate.jar"]

EXPOSE 8980
