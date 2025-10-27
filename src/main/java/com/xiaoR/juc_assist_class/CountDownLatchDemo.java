package com.xiaoR.juc_assist_class;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/27
 * @description 演示减少计数器CountDownLath的使用
 */
@Slf4j
public class CountDownLatchDemo {
    // 6个同学陆续离开教师之后, 班长锁门
    public static void main(String[] args) {
        // 创建CountDownLatch对象, 设置初始值
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                log.info("{} 号同学离开教室", Thread.currentThread().getName());
                countDownLatch.countDown();     // 计数器减一
            }, String.valueOf(i)).start();
        }

        try {
            // 等待计数器归零, 继续执行
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("{} 班长锁门", Thread.currentThread().getName());
    }
}
