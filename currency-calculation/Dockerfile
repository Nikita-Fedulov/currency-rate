FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} currency-calculation.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "currency-calculation.jar"]
