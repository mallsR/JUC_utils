package com.xiaoR.sync;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/21
 * @description
 */

// 1. 创建资源类，定义属性和操作方法
class Ticket {
    private int number = 30;
    public synchronized void sale() {
        if (number > 0) {
            System.out.println(Thread.currentThread().getName() + ": 卖出第" + (number--) + "张票，剩余：" + number);
        }
    }
}

public class SaleTicket {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        // 2. 创建三个线程，调用卖票方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 40; i++) {
                    ticket.sale();
                }
            }
        }, "t1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 40; i++) {
                    ticket.sale();
                }
            }
        }, "t2").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 40; i++) {
                    ticket.sale();
                }
            }
        }, "t3").start();
    }
}
