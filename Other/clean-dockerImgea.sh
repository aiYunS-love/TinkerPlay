#!/bin/bash

# 定义镜像名称和标签
IMAGE_NAME="tinkerplay"
IMAGE_TAG="0.0.1-SNAPSHOT"

# 检查镜像是否存在
if docker image inspect "${IMAGE_NAME}:${IMAGE_TAG}" &> /dev/null; then
    echo "Image exists. Cleaning up..."

    # 查找并停止相关容器
    CONTAINERS=$(docker ps -q --filter "ancestor=${IMAGE_NAME}:${IMAGE_TAG}")
    if [ -n "$CONTAINERS" ]; then
        docker stop $CONTAINERS
        docker rm $CONTAINERS
        echo "Stopped and removed containers."
    fi

    # 删除镜像
    docker rmi "${IMAGE_NAME}:${IMAGE_TAG}"
    echo "Removed image."
else
    echo "Image does not exist."
fi

# 继续执行构建镜像的操作
# docker build -t "${IMAGE_NAME}:${IMAGE_TAG}" .
