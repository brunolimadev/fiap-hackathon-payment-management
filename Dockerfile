# Usamos a imagem base do OpenJDK para o Java 17
FROM maven:3.8.3-openjdk-17-slim AS build

# Copiamos o arquivo pom.xml para a pasta /usr/src/app
COPY pom.xml /usr/src/app/

# Copiamos o código fonte para a pasta /usr/src/app
COPY src /usr/src/app/src/

# Definimos o diretório de trabalho
WORKDIR /usr/src/app

# Compilamos o código fonte
RUN mvn clean install

FROM openjdk:17-slim

# Copiamos o arquivo JAR gerado na etapa anterior para a pasta /usr/app, o comando --from é utilizado para referenciar a imagem anterior
COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar

# Definimos o diretório de trabalho
WORKDIR /usr/app

# Expondo a porta 8084
EXPOSE 8084

# Executamos o JAR
CMD ["java", "-jar", "app.jar"]





