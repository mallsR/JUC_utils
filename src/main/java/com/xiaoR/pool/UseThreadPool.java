package com.xiaoR.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/28
 * @description 演示线程池的使用方式
 */

@Slf4j
public class UseThreadPool {
    public static void main(String[] args) {
        /**
         * 一池多线程 [线程数量固定]
         */
//        ExecutorService bankThreadPool = Executors.newFixedThreadPool(5);

        /**
         * 一池N个线程
         */
//        ExecutorService bankThreadPool = Executors.newSingleThreadExecutor();

        /**
         * 一池N个线程 [线程数量不固定]
         */
        ExecutorService bankThreadPool = Executors.newCachedThreadPool();

        // 10个顾客
        try {
            for (int i = 1; i <= 10; i++) {
                bankThreadPool.execute(() -> { // execute()方法接收的是一个Runnable接口
                    log.debug("{} 为顾客办理业务", Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭: 将线程归还到线程池
            bankThreadPool.shutdown();
        }
    }
}
