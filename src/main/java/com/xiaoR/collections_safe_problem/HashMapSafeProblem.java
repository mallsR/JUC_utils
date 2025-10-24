package com.xiaoR.collections_safe_problem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/24
 * @description 演示HashMap集合中的线程不安全问题及解决办法
 */
public class HashMapSafeProblem {
    public static void main(String[] args) {
        /**
         * 其内部的put方法, 没有加Synchronized关键字, 导致线程不安全
         * public V put(K key, V value) {
         *         return putVal(hash(key), key, value, false, true);
         *     }
         */
//        Map<String, String> map = new HashMap<>();

        // 使用ConcurrentHashMap解决
        Map<String, String> map = new ConcurrentHashMap<>();

        for (int i = 0; i < 100; i++) {
            String key = String.valueOf(i);
            new Thread(() -> {
                map.put(key, UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }
}
