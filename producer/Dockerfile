FROM openjdk:11-jdk-slim
COPY target/producer.jar /opt/producer.jar
ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true", "-XX:+UnlockExperimentalVMOptions", "-jar","/opt/producer.jar"]
EXPOSE 8001
