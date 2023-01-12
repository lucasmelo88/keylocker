FROM openjdk:11
WORKDIR /key-locker
COPY target/key-locker-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]