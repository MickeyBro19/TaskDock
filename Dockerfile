#<--- Single stage docker image --->

#FROM eclipse-temurin:21
#WORKDIR /app
#COPY target/TaskDock-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java" , "-jar", "app.jar"]




#<--- Multistage Docker build --->

#<---stage 1--->
FROM eclipse-temurin:21-jdk as build
WORKDIR /workspace
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw --batch-mode dependency:go-offline
COPY src/ src/
RUN ./mvnw --batch-mode clean package

#<---stage 2--->
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java" , "-jar", "app.jar"]