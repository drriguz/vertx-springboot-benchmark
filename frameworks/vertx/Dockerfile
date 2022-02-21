FROM openjdk:11-jre-slim
COPY build/libs/vertx-1.0.0-SNAPSHOT-fat.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]