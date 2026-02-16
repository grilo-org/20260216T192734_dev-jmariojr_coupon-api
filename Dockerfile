FROM eclipse-temurin:17-jre-alpine
ADD target/cupons-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080