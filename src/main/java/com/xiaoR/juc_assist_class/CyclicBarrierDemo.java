package com.xiaoR.juc_assist_class;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/27
 * @description 演示循环栅栏CylicBarrier的使用
 */
@Slf4j
public class CyclicBarrierDemo {
    /**
     * 集齐7颗龙珠,召唤神龙
     * @param args
     */
    public static void main(String[] args) {
        // 创建CyclicBarrier, 设置屏障点及达成目标后的事件
        CyclicBarrier barrier = new CyclicBarrier(7, () -> {
            log.info("集齐7颗龙珠, 召唤神龙...");
        });

        for (int i = 1; i <= 7; i++) {
            new Thread(() -> {
                try {
                    log.info("{} 号龙珠被收集", Thread.currentThread().getName());
                    // 等待
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
