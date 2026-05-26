FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package

EXPOSE 8081

CMD ["java", "-jar", "target/product-service-0.0.1-SNAPSHOT.jar"]
