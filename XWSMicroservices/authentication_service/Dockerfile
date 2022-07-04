FROM openjdk:11
EXPOSE 8001
ADD target/authentication_service.jar authentication_service.jar
ENTRYPOINT ["java", "-jar", "/authentication_service.jar"]