FROM amazoncorretto:21

COPY target/IAMBackend.jar IAMBackend.jar

ENTRYPOINT ["java", "-jar", "/IAMBackend.jar"]