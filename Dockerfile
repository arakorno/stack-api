FROM openjdk:11-jre-slim

VOLUME /tmp

ADD target/*.jar stack_api.jar

EXPOSE 8080
RUN bash -c 'touch /stack_api.jar'
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongo/test", "-Djava.security.egd=file:/dev/./urandom","-jar","/stack_api.jar"]
