#Separate build step
FROM eclipse-temurin:11-jdk-jammy
RUN mkdir /project
COPY . /project
WORKDIR /project
RUN ./gradlew assemble

FROM eclipse-temurin:11-jdk-jammy
RUN mkdir -p /app
EXPOSE 8080
WORKDIR /app
COPY --from=0 /project/build/libs/cava-tags-0.1.jar /app
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "-Dgrails.env=prod", "cava-tags-0.1.jar"]
