package com.xiaoR.sync;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/24
 * @description 讲解可重入锁的特点
 */
@Slf4j
public class UIYReentrantLock {
    synchronized void enter(int layer) {
        log.info("{} 执行到第 {} 层", Thread.currentThread().getName(), layer);
        if (layer > 10) {
            return;
        }
        enter(layer + 1);
    }

    public static void main(String[] args) {
        Object o = new Object();


        /**
         * 1. 代码块的方式进行演示可重入锁
         */
        new Thread(() -> {
            synchronized (o) {
                log.info("{} 外层", Thread.currentThread().getName());

                synchronized (o) {
                    log.info("{} 中层", Thread.currentThread().getName());

                    synchronized (o) {
                        log.info("{} 内层", Thread.currentThread().getName());
                    }
                }
            }
        }, "t1").start();


        /**
         * 2. 函数调用的方式进行演示可重入锁
         */
        new Thread(() -> {
            new UIYReentrantLock().enter(1);
        }, "t2").start();
    }
}
