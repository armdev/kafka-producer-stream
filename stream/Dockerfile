FROM openjdk:11-jdk-slim
COPY target/stream.jar /opt/stream.jar
ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true", "-XX:+UnlockExperimentalVMOptions", "-jar","/opt/stream.jar"]
EXPOSE 8002
