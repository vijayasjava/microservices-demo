FROM adoptopenjdk/openjdk11:alpine-jre
COPY target/twitter-to-kafka-service-0.0.1-SNAPSHOT.jar /usr/application/
WORKDIR /usr/application
ENTRYPOINT ["java","-jar","twitter-to-kafka-service-0.0.1-SNAPSHOT.jar"]