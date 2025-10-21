package com.xiaoR;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/20
 * @description
 */
public class deamon_thread {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "::" + Thread.currentThread().isDaemon());
            while (true) {

            }
        }, "t1");

        // 设置为守护线程
        t1.setDaemon(true);
        t1.start();

        System.out.println(Thread.currentThread().getName() + "over~");
    }
}
