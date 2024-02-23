FROM openjdk:21

MAINTAINER aiYunS

VOLUME /logs/TinkerPlay
VOLUME /Test/Temp
VOLUME /Test/uploadFile

WORKDIR /TinkerPlay

COPY target/TinkerPlay-0.0.1-SNAPSHOT.jar .

EXPOSE 1001

ENTRYPOINT ["java", "--add-opens=java.base/java.util=ALL-UNNAMED", "-jar","/TinkerPlay/TinkerPlay-0.0.1-SNAPSHOT.jar"]