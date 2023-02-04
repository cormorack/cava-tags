
#FROM openjdk:11
FROM eclipse-temurin:11-jdk-jammy
MAINTAINER John Doe "john.doe@example.com"

EXPOSE 8080

WORKDIR /app
COPY *.jar application.jar

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/application.jar"]

# Deploy the WAR in Tomcat
COPY --from=buildwar /tmp/CavaMedia/build/libs/*.war $CATALINA_HOME/webapps/
