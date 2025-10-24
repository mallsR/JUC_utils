package com.xiaoR.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/23
 * @description
 */
@Slf4j
// 1. 创建资源类, 创建属性及操作方法
class Share {
    private int flag = 1;   // 线程运行标志位, 1: t1运行, 2: t2运行, 3: t3运行;

    // 创建锁
    private final Lock lock = new ReentrantLock();

    // 创建条件控制
    private final Condition condition1 = lock.newCondition();
    private final Condition condition2 = lock.newCondition();
    private final Condition condition3 = lock.newCondition();

    public void print5(int loop) throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            // 2.1 判断
            while (flag != 1) {
                condition1.await();
            }

            // 2.2 干活: 输出5
            log.info("------------------------------");
            for (int i = 0; i < 5; i++) {
                log.info("{} 进行第 {} 轮工作, 第 {} 次循环", Thread.currentThread().getName(), i + 1, loop);
            }
            log.info("------------------------------");

            // 2.3 改变标志位,通知其他线程
            flag = 2;
            condition2.signal();
        } finally {
            // 解锁
            lock.unlock();
        }
    }

    public void print10(int loop) throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            // 2.1 判断
            while (flag != 2) {
                condition2.await();
            }

            // 2.2 干活: 输出5
            log.info("------------------------------");
            for (int i = 0; i < 10; i++) {
                log.info("{} 进行第 {} 轮工作, 第 {} 次循环", Thread.currentThread().getName(), i + 1, loop);
            }
            log.info("------------------------------");

            // 2.3 改变标志位,通知其他线程
            flag = 3;
            condition3.signal();
        } finally {
            // 解锁
            lock.unlock();
        }
    }


    public void print15(int loop) throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            // 2.1 判断
            while (flag != 3) {
                condition3.await();
            }

            // 2.2 干活: 输出5
            log.info("------------------------------");
            for (int i = 0; i < 15; i++) {
                log.info("{} 进行第 {} 轮工作, 第 {} 次循环", Thread.currentThread().getName(), i + 1, loop);
            }
            log.info("------------------------------");

            // 2.3 改变标志位,通知其他线程
            flag = 1;
            condition1.signal();
        } finally {
            // 解锁
            lock.unlock();
        }
    }
}

public class LDIYCommunication {

    public static void main(String[] args) {
        Share share = new Share();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.print5(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t1").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.print10(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t2").start();


        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.print15(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t3").start();
    }
}
