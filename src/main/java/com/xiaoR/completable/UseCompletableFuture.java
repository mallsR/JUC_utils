package com.xiaoR.completable;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/28
 * @description  演示异步回调CompletableFuture的常用方式
 */

@Slf4j
public class UseCompletableFuture {
    public static void main(String[] args) throws Exception {
        // completableFuture 异步回调, 无返回值
        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
            log.info("{}, completableFuture1", Thread.currentThread().getName());
        });
        completableFuture1.get();

        // completableFuture 异步回调, 有返回值
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            log.info("{}, completableFuture2", Thread.currentThread().getName());
            int i = 10 / 0;
            return 1024;
        });
        completableFuture2.whenComplete((t, u) -> {
            log.info("completableFuturen2 t = {}", t);  // 方法返回值
//            System.out.println("completableFuture2 u = " + u);
            log.info("completableFuturen2 u = {}", u);  // 得到返回值过程中的异常
        }).get();
    }
}

