# Etapa de compilación
FROM maven:3.9.6-eclipse-temurin-21-jammy AS build
WORKDIR /app
# Copiar el pom.xml y descargar las dependencias primero (aprovechar cache de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline -B
# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Exponer el puerto de la aplicación
EXPOSE 8085
# Copiar el jar compilado desde la etapa de build
COPY --from=build /app/target/medicronos-0.0.1-SNAPSHOT.jar app.jar
# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
