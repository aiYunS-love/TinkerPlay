FROM openjdk:21

MAINTAINER baiyx

WORKDIR /TinkerPlay

COPY target/TinkerPlay-0.0.1-SNAPSHOT.jar .

EXPOSE 1001

ENTRYPOINT ["java", "--add-opens=java.base/java.util=ALL-UNNAMED", "-jar","/TinkerPlay/TinkerPlay-0.0.1-SNAPSHOT.jar"]