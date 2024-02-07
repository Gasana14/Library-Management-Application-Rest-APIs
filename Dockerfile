FROM openjdk:17

LABEL mentainer="gasana1414@gmail.com"

WORKDIR /app

COPY target/Library-Management-Rest-APIs-0.0.1-SNAPSHOT.jar /app/springboot-library-management.jar

ENTRYPOINT ["java", "-jar", "springboot-library-management.jar"]