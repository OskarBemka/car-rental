FROM maven:3.8.1-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM openjdk:17.0.2-slim-buster AS runtime

WORKDIR /app
COPY --from=build /app/target/car-rental-0.1.jar .
COPY pom.xml .

ENTRYPOINT ["java","-jar","/app/car-rental-0.1.jar"]
