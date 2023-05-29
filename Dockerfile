# 该镜像需要依赖的基础镜像
FROM java:8
# 将当前maven目录生成的文件复制到docker容器的/目录下
COPY maven /
# 声明服务运行在1001端口
EXPOSE 1001
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java", "-jar","/wfwbitest-1.0.1-SNAPSHOT.jar"]
# 指定维护者的名字
MAINTAINER baiyx