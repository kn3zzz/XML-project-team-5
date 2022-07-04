FROM openjdk:11
EXPOSE 8000
ADD target/api_gateway.jar api_gateway.jar
ENTRYPOINT ["java", "-jar", "/api_gateway.jar"]