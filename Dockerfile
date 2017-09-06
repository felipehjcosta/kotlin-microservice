FROM openjdk:8u121-jdk-alpine

RUN apk add --no-cache bash curl

COPY gradle.properties /root/.gradle/

COPY . /project
WORKDIR /project

RUN ./gradlew clean jar

RUN mv app/build/libs/app.jar /usr/local/app.jar
RUN rm -rf /root/.gradle /project

WORKDIR /usr/local/

CMD ["java", "-jar", "app.jar"]

EXPOSE 8080