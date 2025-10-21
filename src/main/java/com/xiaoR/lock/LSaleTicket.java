package com.xiaoR.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/21
 * @description
 */
// 1. 创建资源类，定义属性和操作方法
class LTicket {
    private int number = 30;

    private final ReentrantLock lock = new ReentrantLock();     // 创建可重入锁
    public void sale() {
        // 上锁
        lock.lock();
        try {       // 此处使用try-finally块，保证即使出现异常, 锁也一定能被释放
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + ": 卖出第" + (number--) + "张票，剩余：" + number);
            }
        } finally {
            // 开锁
            lock.unlock();
        }
    }
}

public class LSaleTicket {
    public static void main(String[] args) {
        // 2. 创建三个线程，调用卖票方法
        LTicket ticket = new LTicket();

        // 使用lambda表示式实现线程
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        // 注意: 此处start()时,线程不一定立马创建.
        // start()内部的start0()有一个native方法, 会调用操作系统的线程创建方法, 具体创建时间由操作系统自行决定
        }, "t1").start();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "t2").start();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "t3").start();
    }
}
