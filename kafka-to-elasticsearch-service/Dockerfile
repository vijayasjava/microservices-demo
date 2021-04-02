FROM adoptopenjdk/openjdk11
COPY target/${project.artifactId}-${project.version}.jar /usr/application/
WORKDIR /usr/application
ENTRYPOINT ["java","-jar","${project.artifactId}-${project.version}.jar"]