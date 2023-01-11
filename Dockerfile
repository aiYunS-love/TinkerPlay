# 基础镜像通过openjdk:8-jdk-alpine打包
FROM openjdk:8-jdk-alpine

ENV SERVER_PORT 8082

ADD wfwbitest.jar app.jar

COPY application.yml application.yml

# 将当前文件中所有的*.jar 拷贝到项目的app.jar中(这个app.jar是自己生成的)
# COPY *.jar /app.jar
# 映射地址
# CMD ["--server.port=8082"]
# 暴露端口
# EXPOSE 8082
# 执行命令java -jar
ENTRYPOINT ["java","-jar","/app.jar"]