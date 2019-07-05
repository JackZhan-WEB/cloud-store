FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/cloud-store-eureka.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar
