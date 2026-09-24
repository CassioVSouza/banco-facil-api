# Etapa 1 (build): JDK completo + Maven, usado apenas para compilar o .jar.
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /build

COPY pom.xml .
RUN mvn --batch-mode dependency:go-offline

COPY src ./src
RUN mvn --batch-mode -DskipTests package

# Etapa 2 (runtime): apenas o JRE, com tag fixada (sem "latest"). O JDK, o
# Maven e o codigo-fonte ficam na etapa de build e nao vao para a imagem final.
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Usuario sem privilegios (Principio do Menor Privilegio): o processo nao roda como root.
RUN addgroup -S app && adduser -S app -G app

COPY --from=build --chown=app:app /build/target/banco-facil-api-0.0.1-SNAPSHOT.jar app.jar

USER app

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
