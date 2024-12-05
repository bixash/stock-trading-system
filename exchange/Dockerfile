FROM openjdk:22-oracle
COPY target/*.jar ExchangeApp.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "ExchangeApp.jar"]