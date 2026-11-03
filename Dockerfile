# Étape 1 : compilation du projet avec Maven et Java 21
FROM maven:3.9-eclipse-temurin-21 AS construction
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Les tests sont lancés en local (mvn verify) : ils ont besoin d'une base, absente pendant la construction
RUN mvn -B package -DskipTests

# Étape 2 : image finale légère, avec seulement Java 21 et le .jar
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=construction /app/target/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]