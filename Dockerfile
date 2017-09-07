FROM openjdk:8u121-jdk-alpine

COPY app/build/libs/app.jar /usr/local/app.jar

WORKDIR /usr/local/

CMD ["java", "-jar", "app.jar"]

EXPOSE 8080