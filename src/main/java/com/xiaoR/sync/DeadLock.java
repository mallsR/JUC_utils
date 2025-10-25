package com.xiaoR.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/25
 * @description 演示死锁发生的情况
 */
@Slf4j
public class DeadLock {
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();

        new Thread(() -> {
            synchronized (o1) {
                log.info("{} 获取锁 o1, 等待锁 o2", Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (o2) {     // 互相等待对方拥有的资源, 造成死锁
                    log.info("{} 获取锁 o2", Thread.currentThread().getName());
                }
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (o2) {
                log.info("{} 获取锁 o2, 等待锁 o1", Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (o1) {     // 互相等待对方拥有的资源, 造成死锁
                    log.info("{} 获取锁 o1", Thread.currentThread().getName());
                }
            }
        }, "t2").start();
    }
}
