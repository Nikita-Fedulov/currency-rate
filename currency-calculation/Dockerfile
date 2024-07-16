FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY build/libs/currency-calculation-0.0.1-Calk-plain.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app.jar"]