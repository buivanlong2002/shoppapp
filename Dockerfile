FROM openjdk:22-jdk-slim
WORKDIR /app/long
COPY target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"] 
