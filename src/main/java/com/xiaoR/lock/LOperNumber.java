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
// 1. 创建资源类, 定义属性和操作方法
class Number {
    private int number = 0;

    // 创建lock
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    // +1
    public void increment() throws Exception {
        // 上锁
        lock.lock();
        try {
            // 2.1 判断
            while (number > 0) {    // 避免虚假唤醒
                condition.await();  // 线程等待
            }
            // 2.2 干活: 具体操作
            number++;
            log.info("线程：{}，number:{}", Thread.currentThread().getName(), number);
            // 2.3 通知其他线程
            condition.signalAll();
        } finally {
            // 开锁
            lock.unlock();
        }
    }

    // -1
    public void decrement() throws Exception {
        // 上锁
        lock.lock();
        try {
            // 2.1 判断
            while (number <= 0) {    // 避免虚假唤醒
                condition.await();  // 线程等待
            }
            // 2.2 干活: 具体操作
            number--;
            log.info("线程：{}，number:{}", Thread.currentThread().getName(), number);
            // 3.3 通知其他线程
            condition.signalAll();
        } finally {
            // 开锁
            lock.unlock();
        }
    }
}

public class LOperNumber {
    public static void main(String[] args) {
        Number number = new Number();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    number.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    number.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "t2").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    number.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "t3").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    number.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "t4").start();
    }
}
