package com.xiaoR.use_callable;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/25
 * @description 演示callable接口实现线程的使用
 */
@Slf4j
class RunnableThread implements Runnable{
    @Override
    public void run() {
        log.info("从Runnable实现的线程成功启动...");
    }
}

@Slf4j
class CallableThread implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        log.info("从Callable实现的线程成功启动...");
        return 1024;
    }
}

@Slf4j
public class CallableDesc {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new Thread(new RunnableThread(), "thread-r1").start();
        // new Thread(new CallableThread(), "thread-c1").start();  // 无法直接从Callable创建线程

        /**
         * 通过FutureTask类接收Callable对象, 用于实例化线程
         * FutureTask类实现了Runnable接口, 但其内部包含一个Callable对象, 创建线程时, 会调用Callable对象中的call()方法
         * FutureTask的特点:
         *    1. 先完成容易做的任务
         *    2. 只需要计算一次, 后续调用,直接返回结果
         */
        // 此种方法,需要声明一个具体的实现了Callable接口的类
        FutureTask<Integer> futureTask1 = new FutureTask<>(new CallableThread());
        // 采用匿名表达式的方式进行简化
        FutureTask<Integer> futureTask2 = new FutureTask<>(() -> {
            log.info("从Callable实现的线程成功启动...");
            return 1024;
        });

        new Thread(futureTask1, "thread-c1").start();
        new Thread(futureTask2, "thread-c2").start();

        while (!futureTask1.isDone()) {
            log.info("等待...");
        }
        while (!futureTask2.isDone()) {
            log.info("等待...");
        }

        log.info("futureTask1线程的返回值: {}", futureTask1.get());
        log.info("futureTask2线程的返回值: {}", futureTask2.get());
    }
}
