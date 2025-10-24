package com.xiaoR.collections_safe_problem;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/10/24
 * @description 演示List集合中的线程不安全问题
 */
@Slf4j
public class ListSafeProblem {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                /**
                 * public boolean add(E e) {
                 *         modCount++;
                 *         add(e, elementData, size);
                 *         return true;
                 *     }
                 *     由于add方法并没有加Synchronized关键字, 即线程在添加元素时没有加锁.
                 *     另外的线程通过System.out.println(list);读取ArrayList的元素时,造成 [并发修改问题 ]
                 *     [ Exception in thread "53" java.util.ConcurrentModificationException ]
                 */
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);   //
            }, String.valueOf(i)).start();
        }
    }
}
