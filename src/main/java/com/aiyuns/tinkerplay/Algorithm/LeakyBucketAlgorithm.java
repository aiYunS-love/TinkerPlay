package com.aiyuns.tinkerplay.Algorithm;

import java.util.concurrent.TimeUnit;

/**
 * @Author: aiYunS
 * @Date: 2023年12月25日, 0025 上午 9:26:46
 * @Description: 漏桶算法
 */

    public class LeakyBucketAlgorithm {
    private final int capacity; // 桶的容量
    private int waterLevel; // 当前水位
    private long lastLeakTime; // 上一次漏水的时间
    private final long leakInterval; // 漏水时间间隔

    public LeakyBucketAlgorithm(int capacity, long leakInterval, TimeUnit timeUnit) {
        this.capacity = capacity;
        this.waterLevel = 0;
        this.lastLeakTime = System.currentTimeMillis();
        this.leakInterval = timeUnit.toMillis(leakInterval);
    }

    public synchronized boolean tryConsume(int water) {
        leak(); // 漏水
        if (waterLevel + water <= capacity) {
            waterLevel += water;
            return true; // 执行业务逻辑
        } else {
            return false; // 限流，执行相应的处理逻辑
        }
    }

    private void leak() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastLeakTime;
        long leaked = elapsedTime / leakInterval; // 计算漏水量
        waterLevel = Math.max(0, waterLevel - (int) leaked);
        lastLeakTime = currentTime;
    }

    public static void main(String[] args) {
        LeakyBucketAlgorithm leakyBucket = new LeakyBucketAlgorithm(10, 1000, TimeUnit.MILLISECONDS);
        for (int i = 0; i < 15; i++) {
            if (leakyBucket.tryConsume(1)) {
                System.out.println(i + ": 执行业务逻辑");
            } else {
                System.out.println(i + ": 限流处理");
            }
            try {
                Thread.sleep(100); // 模拟请求间隔
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
