package com.xiaoR.juc_assist_class;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/27
 * @description 信号灯Semaphore使用示例
 */
@Slf4j
public class SemaphoreDemo {
    /**
     * 6辆车抢3个停车位
     * @param args
     */
    public static void main(String[] args) {
        // 创建信号量
        Semaphore semaphore = new Semaphore(3);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    // 抢占车位
                    semaphore.acquire();
                    log.info("第 {} 辆车抢到停车位...", Thread.currentThread().getName());

                    // 设置停车时间: 5秒以内
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));

                    log.info("第 {} 辆车离开...", Thread.currentThread().getName());
                    log.info("-----------------------------------------");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    semaphore.release();    // 释放停车位
                }
            }, String.valueOf(i)).start();
        }
    }
}
