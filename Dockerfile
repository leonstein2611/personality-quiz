# 1. Schritt: Bauen
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .

# Dieser Befehl sucht die pom.xml und wechselt in das richtige Verzeichnis, falls nötig
RUN find . -name "pom.xml" -exec dirname {} \; > folder.txt && \
    cd $(cat folder.txt) && \
    mvn clean package -DskipTests

# 2. Schritt: Ausführen
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
# Wir suchen die gebaute .jar Datei, egal in welchem Unterordner sie gelandet ist
COPY --from=build /app/**/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]