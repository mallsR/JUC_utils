package com.xiaoR.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/24
 * @description 使用Lock演示可重入锁的特性
 */
@Slf4j
public class LUIYReentrantLock {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        new Thread(() -> {
            try {
                lock.lock();
                log.info("{} 进入 外层.", Thread.currentThread().getName());

                try {
                    lock.lock();
                    log.info("{} 进入 内层.", Thread.currentThread().getName());
                } finally {
                    log.info("{} 退出 内层.", Thread.currentThread().getName());
                    lock.unlock();
                    /**
                     * 注意, 虽然即使此处不进行lock.unlock(), t1线程也能正常执行
                     * 但是, 此处不进行lock.unlock(), 其他使用到这个锁的线程将无法继续往下执行.
                     */
                }
            } finally {
                log.info("{} 退出 外层.", Thread.currentThread().getName());
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            try {
                lock.lock();    // 由于t1线程已经占用了锁, t2线程将无法获取锁, 处于阻塞状态
                log.info("{} 进入.", Thread.currentThread().getName());
            } finally {
                log.info("{} 退出.", Thread.currentThread().getName());
                lock.unlock();
            }
        }, "t2").start();
    }
}
