package com.xiaoR.read_write_lock;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/27
 * @description 演示读写锁的使用
 */
@Slf4j
class MyCache {
    private volatile Map<String, Object> map = new HashMap<>(); // volatile用于标识map会被经常修改

    /**
     * 创建读写锁对象, 共享读和唯一写, 读写操作加锁略有不同,虽然都是用ReentrantReadWriteLock
     */
    ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();  // 创建读写锁对象

    public void put(String key, Object value) {
        rwl.writeLock().lock();     // 写操作, 加锁
        try {
            log.info("{} 正在写入数据...", Thread.currentThread().getName());
            map.put(key, value);

            // 等待写入
            TimeUnit.MICROSECONDS.sleep(300);

            log.info("{} 写入数据成功...", Thread.currentThread().getName());
            log.info("-------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwl.writeLock().unlock();       // 释放锁
        }
    }

    public Object get(String key) {
        rwl.readLock().lock();      // 读操作, 加锁
        Object result = null;

        try {
            log.info("{} 正在读取数据...", Thread.currentThread().getName());
            result = map.get(key);

            // 等待
            TimeUnit.MICROSECONDS.sleep(300);

            log.info("{} 读取数据成功...", Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwl.readLock().unlock();        // 释放锁
        }

        return result;
    }
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        // 创建缓存对象: 资源
        MyCache myCache = new MyCache();

        // 创建5个写线程
        for (int i = 1; i <= 5; i++) {
            final int num = i;
            new Thread(() -> {
                myCache.put(String.valueOf(num), String.valueOf(num));
            }, "read_thread-" + i).start();
        }

        // 创建5个读线程
        for (int i = 1; i <= 5; i++) {
            final int num = i;
            new Thread(() -> {
                myCache.get(String.valueOf(num));
            }, "write_thread-" + i).start();
        }
    }
}
