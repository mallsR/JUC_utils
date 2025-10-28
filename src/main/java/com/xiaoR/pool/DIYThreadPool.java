package com.xiaoR.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/28
 * @description 自定义线程池
 */

@Slf4j
public class DIYThreadPool {
    public static void main(String[] args) {
        // 自定义线程池
        ThreadPoolExecutor diyThreadPool = new ThreadPoolExecutor(
                2, 5, 2, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),    // 阻塞队列
                Executors.defaultThreadFactory(),       // 默认线程工厂
                new ThreadPoolExecutor.AbortPolicy()    // 默认拒绝策略
        );

        // 服务顾客
        try {
            for (int i = 1; i <= 10; i++) {
                diyThreadPool.execute(() -> {
                    log.info("{} 为顾客办理业务", Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            diyThreadPool.shutdown();   // 关闭线程池: 将线程返回给线程池
        }
    }
}
