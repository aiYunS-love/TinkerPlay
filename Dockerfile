# 该镜像需要依赖的基础镜像
FROM openjdk:21
# 指定维护人
MAINTAINER baiyx
# 将当前maven目录生成的文件复制到docker容器的/目录下
COPY maven /
# 声明服务运行在1001端口
EXPOSE 1001
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java", "--add-opens=java.base/java.util=ALL-UNNAMED", "-jar","/wfwbitest-1.0.1-SNAPSHOT.jar"]