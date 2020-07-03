package com.study.collection;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author harry
 * @create 2020-06-30 15:21
 * @Version 1.0
 * 集合类不安全的问题
 * ArrayList
 */
public class ContainerNotSafeDemo {

    public static void main(String[] args){

        notSafe();
    }

    /**
     * 故障现象
     * java.util.ConcurrentModificationException
     */
    public static void notSafe() {

//        List<String> list = new ArrayList<>();
//        List<String> list = new Vector<>();  //Vector线程安全
//        List<String> list = Collections.synchronizedList(new ArrayList<>());  //使用辅助类
        List<String> list = new CopyOnWriteArrayList<>();      //写时复制，读写分离
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, "Thread " + i).start();
        }
    }

    /**
     * 1 故障现象
     * 2 导致原因
     * 3 解决方案
     *     3.1 new Vector<>();
     *     3.2 Collections.synchronizedList(new ArrayList<>());
     *     3.3 new CopyOnWriteArrayList<>();
     * 4 优化建议（同样的错误不犯第2次）
     */
}
