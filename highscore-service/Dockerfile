FROM openjdk:11

EXPOSE 8082
COPY target/highscore-service-0.0.1-SNAPSHOT.jar /
CMD java -jar highscore-service-0.0.1-SNAPSHOT.jar

#VOLUME /tmp
#ADD target/highscore-service-0.0.1-SNAPSHOT.jar app.jar
#ENV JAVA_OPTS=""
#ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar --server.port=8082" ]
