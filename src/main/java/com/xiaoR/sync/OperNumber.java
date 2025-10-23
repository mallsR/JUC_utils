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
        while (number > 0) {       // xky001 TODO 2025/10/22: 此处用if会有虚假唤醒问题, 需要用while
            this.wait();    // 在那里睡, 就在哪里醒.
            // 在多线程环境下, 初次进入条件,线程等待,后续其他线程执行完毕又把当前线程唤醒, 则当前线程就跟着执行后续的number++, 导致出现问题
            // 如果把if改为while,每次唤醒后,都会继续判断number > 0 ?, 当条件不成立时, 则会跳出while循环, 执行number++
        }
        // 2.2 干活: 具体操作
        number++;
        log.info("线程：{}，number:{}", Thread.currentThread().getName(), number);
        // 2.3 通知: 通知其他线程自己已释放资源
        this.notifyAll();
    }

    public synchronized void decrement() throws Exception {
        // 2.1 判断
        while (number <= 0) {   // 将原本的if 改为while, 为了避免虚假唤醒. 避免线程被唤醒后,不再进行判断,从而错误执行number--
            this.wait();
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

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "t3").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "t4").start();
    }
}
