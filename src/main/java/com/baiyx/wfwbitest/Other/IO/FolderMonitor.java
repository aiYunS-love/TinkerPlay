package com.baiyx.wfwbitest.Other.IO;

import java.io.IOException;
import java.nio.file.*;

/**
 * @Author: baiyx
 * @Date: 2023年10月2日, 0002 上午 9:59:43
 * @Description: 对某一路径下的文件夹进行监测
 */
public class FolderMonitor {
    public static void main(String[] args) throws IOException {
        // 指定要监测的路径
        Path pathToMonitor = Paths.get("E:\\baiyx\\xiaobai-studies\\logs\\wfwbitest");

        // 创建一个WatchService对象
        WatchService watchService = FileSystems.getDefault().newWatchService();

        // 注册要监测的文件夹
        pathToMonitor.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE);

        // 启动监测线程
        Thread watchThread = new Thread(() -> {
            while (true) {
                try {
                    WatchKey key = watchService.take(); // 等待事件
                    for (WatchEvent<?> event : key.pollEvents()) {
                        // 处理事件
                        WatchEvent.Kind<?> kind = event.kind();
                        Path eventPath = (Path) event.context();
                        System.out.println("Event: " + kind + " - File: " + eventPath);
                    }
                    key.reset(); // 重置WatchKey以便继续监测
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        watchThread.setDaemon(true);
        watchThread.start();
    }
}
