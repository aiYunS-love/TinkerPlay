package com.aiyuns.tinkerplay.Algorithm;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

/**
 * @Author: aiYunS
 * @Date: 2023年12月25日, 0025 上午 9:19:04
 * @Description: 令牌桶算法
 */

public class TokenBucketAlgorithm {
    private final int capacity; // 令牌桶容量
    private double tokens; // 当前令牌数量，使用 double 类型以支持部分令牌
    private long lastRefillTime; // 上一次令牌补充时间
    private final long refillInterval; // 令牌补充时间间隔
    private final Lock lock; // 用于保证线程安全

    public TokenBucketAlgorithm(int capacity, long refillInterval, TimeUnit timeUnit) {
        this.capacity = capacity;
        this.tokens = 0;
        this.lastRefillTime = System.currentTimeMillis();
        this.refillInterval = timeUnit.toMillis(refillInterval);
        this.lock = new ReentrantLock();
    }

    public boolean tryConsume(int tokens) {
        lock.lock();
        try {
            refillTokens(); // 补充令牌
            if (tokens <= this.tokens) {
                this.tokens -= tokens;
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    private void refillTokens() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastRefillTime;
        double tokensToAdd = elapsedTime / (double) refillInterval; // 补充的令牌数量
        this.tokens = Math.min(capacity, this.tokens + tokensToAdd);
        this.lastRefillTime = currentTime;
    }

    public static void main(String[] args) {
        TokenBucketAlgorithm tokenBucket = new TokenBucketAlgorithm(10, 1000, TimeUnit.MILLISECONDS);
        for (int i = 0; i < 100; i++) {
            if (tokenBucket.tryConsume(1)) {
                System.out.println(i + ": 执行业务逻辑"); // 执行业务逻辑
            } else {
                System.out.println(i + ": 限流处理"); // 限流处理
            }
            try {
                Thread.sleep(100); // 模拟请求间隔
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
