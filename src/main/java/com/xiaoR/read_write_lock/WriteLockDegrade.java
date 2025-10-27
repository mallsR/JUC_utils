package com.xiaoR.read_write_lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/27
 * @description 演示锁降级的操作
 */

@Slf4j
public class WriteLockDegrade {
    public static void main(String[] args) {
        // 读写锁对象
        ReentrantReadWriteLock rwlLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = rwlLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = rwlLock.writeLock();

        // 写锁降级步骤 [写锁降级为读锁]
        // 1. 获取写锁
        writeLock.lock();
        log.info("获取写锁...");

        // 2. 获取读锁
        readLock.lock();
        log.info("获取读锁...");

        // 3. 释放写锁
        writeLock.unlock();

        // 4. 释放读锁
        readLock.unlock();
        log.info("------写锁降级操作完成------");

        // 读锁不能升级为写锁
        readLock.lock();
        log.info("获取读锁...");

        writeLock.lock();
        log.info("获取写锁...");
    }
}
