package com.xiaoR.forkjoin;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/28
 * @description 演示分支合并的使用
 */
// 注意需要将自己的任务继承自分支合并任务
class AddTask extends RecursiveTask<Integer> {
    private static final Integer MAX_DISTANCE = 10;
    private int begin;
    private int end;
    private int result;

    public AddTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Integer compute() {   // 分支合并的操作函数
        if (end - begin <= MAX_DISTANCE) {
            for (int i = begin; i <= end; i++) {
                result += i;
            }
            return result;
        } else {
            int middle = (begin + end) / 2;     // 获取中间值
            AddTask left = new AddTask(begin, middle);      // 拆分左边
            AddTask right = new AddTask(middle + 1, end);   // 拆分右边
            // 调用方法进行拆分
            left.fork();
            right.fork();
            // 合并结果
            return left.join() + right.join();
        }
    }
}

@Slf4j
public class UseForkJoin {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建自己的任务
        AddTask addTask = new AddTask(1, 100);

        // 创建分支合并池对象
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(addTask);

        // 获取最终合并之后的结果
        Integer result = forkJoinTask.get();
        log.info("计算结果: {}", result);

        // 关闭池对象
        forkJoinPool.shutdown();
    }
}
