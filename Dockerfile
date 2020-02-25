FROM openjdk:11-jre-slim

VOLUME /tmp

ADD target/*.jar app.jar

EXPOSE 8080
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongo/test", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
