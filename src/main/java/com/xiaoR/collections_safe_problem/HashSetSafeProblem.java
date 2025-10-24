package com.xiaoR.collections_safe_problem;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/24
 * @description 演示HashSet集合中的线程不安全问题及解决方法
 */
public class HashSetSafeProblem {
    public static void main(String[] args) {

        /**
         * 由于HashSet集合的add方法源码中没有加Synchronized关键字, 所以有线程安全问题
         * public boolean add(E e) {
         *         return map.put(e, PRESENT)==null;
         *     }
         */
//        Set<String> set = new HashSet<>();

        // 使用CopyOnWriteArraySet解决
        Set<String> set = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }
}
