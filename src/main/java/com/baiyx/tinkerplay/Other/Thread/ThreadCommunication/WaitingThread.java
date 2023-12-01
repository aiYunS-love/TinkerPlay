package com.baiyx.tinkerplay.Other.Thread.ThreadCommunication;

/**
 * @Author: baiyx
 * @Date: 2023年12月1日, 0001 上午 11:25:26
 * @Description: 模拟线程通信
 */

public class WaitingThread extends Thread{
    private SharedResource sharedResource;

    public WaitingThread(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        System.out.println("WaitingThread 正在等待...");
        sharedResource.waitForFlag();
        System.out.println("WaitingThread 收到通知，继续执行...");
    }
}
