package com.xiaoR.sync;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/22
 * @description
 */

@Slf4j
// 1. 创建资源类,创建属性及操作方法
class Share {
    private int number = 0;

    // 2. 创建线程方法
    public synchronized void increment() throws Exception {
        // 2.1 判断
        if (number > 0) {
            wait();
        }
        // 2.2 干活: 具体操作
        number++;
        log.info("线程：{}，number:{}", Thread.currentThread().getName(), number);
        // 2.3 通知: 通知其他线程自己已释放资源
        this.notifyAll();
    }

    public synchronized void decrement() throws Exception {
        // 2.1 判断
        if (number <= 0) {
            wait();
        }
        // 2.2 干活: 具体操作
        number--;
        log.info("线程：{}，number:{}", Thread.currentThread().getName(), number);
        // 2.3 通知: 通知其他线程自己已释放资源
        this.notifyAll();
    }
}

public class OperNumber {
    // 3. 创建线程对象,调用资源类的操作方法
    public static void main(String[] args) {
        Share share = new Share();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "t2").start();
    }
}
