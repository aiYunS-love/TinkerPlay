package com.baiyx.tinkerplay.Other.Thread.ThreadPool;

import java.util.concurrent.*;

/**
 * @Author: baiyx
 * @Date: 2023年11月8日, 0008 上午 11:06:31
 * @Description: 自定义线程池
 */

public class CustomThreadPool {
    public static void main(String[] args) {
        // 创建一个自定义线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2, // 核心线程数
                4, // 最大线程数
                10, // 空闲线程等待时间
                TimeUnit.SECONDS, // 时间单位
                new ArrayBlockingQueue<>(2), // 任务队列
                new CustomThreadFactory(), // 自定义线程工厂
                new CustomRejectionPolicy() // 自定义拒绝策略
        );

        // 提交任务给线程池
        for (int i = 1; i <= 10; i++) {
            final int taskNumber = i;
            executor.execute(() -> System.out.println("Task " + taskNumber + " is running."));
        }

        // 关闭线程池
        executor.shutdown();
    }

    // 自定义线程工厂
    static class CustomThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("CustomThread-" + thread.getId());
            return thread;
        }
    }

    // 自定义拒绝策略
    static class CustomRejectionPolicy implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("Task " + r.toString() + " has been rejected.");
        }
    }
}
