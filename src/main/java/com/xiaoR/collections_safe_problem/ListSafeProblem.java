package com.xiaoR.collections_safe_problem;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/24
 * @description 演示List集合中的线程不安全问题
 */
@Slf4j
public class ListSafeProblem {
    public static void main(String[] args) {
        /**
         * ArrayList的add方法源码
         * public boolean add(E e) {
         *         modCount++;
         *         add(e, elementData, size);
         *         return true;
         *     }
         *     由于add方法并没有加Synchronized关键字, 即线程在添加元素时没有加锁.
         *     另外的线程通过System.out.println(list);读取ArrayList的元素时,造成 [并发修改问题 ]
         *     [ Exception in thread "53" java.util.ConcurrentModificationException ]
         */
//        List<String> list = new ArrayList<>();     // 存在线程安全问题


        /**
         * 解决线程不安全问题
         */
        // 1. 使用Vector替代ArrayList [不推荐]
//        List<String> list = new Vector<>();

        // 2. 使用Collections.synchronizedList(new ArrayList<>()) [不推荐]
//        List<String> list = Collections.synchronizedList(new ArrayList<>()); // synchronizedList方法能返回一个线程安全的集合

        // 3. 使用CopyOnWriteArrayList [ 推荐 ]
        List<String> list = new CopyOnWriteArrayList<>();
        /**
         * 其内部add方法的源码
            public boolean add(E e) {
                synchronized (lock) {
                    Object[] es = getArray();
                    int len = es.length;
                    es = Arrays.copyOf(es, len + 1);
                    es[len] = e;
                    setArray(es);
                    return true;
                }
            }
         */

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);   //
            }, String.valueOf(i)).start();
        }
    }
}
