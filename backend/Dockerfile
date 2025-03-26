FROM openjdk:17.0-jdk

WORKDIR /app

COPY build/libs/demo-0.0.1-SNAPSHOT.jar .
EXPOSE 7999

CMD [ "java", "-jar", "demo-0.0.1-SNAPSHOT.jar" ]