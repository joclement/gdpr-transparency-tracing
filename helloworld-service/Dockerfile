FROM openjdk:11
VOLUME /tmp
ADD target/helloworld-service-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
#-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -XshowSettings:vm "
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar --server.port=8081" ]
