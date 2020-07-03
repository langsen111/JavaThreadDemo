package com.study.conditionThread;

import java.util.concurrent.*;

/**
 * @author harry
 * @create 2020-07-03 11:43
 * @Version 1.0
 *
 * 第4中获得/使用java多线程的方式，线程池
 */
public class MythreadPoolDemo {

    // Array Arrays
    // Collection Collections
    // Executor Executors
    public static void main(String[] args) {

        System.out.println(Runtime.getRuntime().availableProcessors());   // 获取cpu核心数

        /*
            CPU密集型：该任务需要大量的运算，没有阻塞，CPU一直全速运行
            CPU密集任务只有在真正的多核CPU上才可能得到加速（通过多线程）

            CPU密集型任务配置尽可能少的线程数量：
                一般公式：CPU核数 + 1 个线程的线程池
         */

        /*
            IO密集型的线程并不是一直在执行任务，则应配置尽可能多的线程，如CPU核数 * 2

            一般生产中：参考公式：CPU核数 / （1 - 阻塞系数）      阻塞系数在0.8～0.9之间
            比如8核CPU： 8 /（1-0.9）= 80 个线程数
         */

        ExecutorService threadPool = new ThreadPoolExecutor(
                2,        // 池正常线程数量
                5,    // 线程池的最大数量
                1L,      // 时间
                TimeUnit.SECONDS,     // 单位
                new LinkedBlockingQueue<Runnable>(3),    // 阻塞队列数，链表阻塞队列，默认21亿
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());     // 内置拒绝策略    abortPolicy 默认，直接抛异常；CallerRunsPolicy 返回调用者；DiscardOldestPlicy 抛弃耗时长的线程；Discardpolicy 直接丢弃任务

        // 模拟10个用户来办理业务，每个用户就是一个来自外部的请求线程
        try{
            for(int i = 1; i <= 40; i++){
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            threadPool.shutdown();
        }

    }

    public static void originMain(String[] args) {
//        ExecutorService threadPool = Executors.newFixedThreadPool(5 ); // 一池5个处理线程
//        ExecutorService threadPool = Executors.newSingleThreadExecutor(); // 一池1个处理线程
        ExecutorService threadPool = Executors.newCachedThreadPool(); // 一池N个处理线程

        // 模拟10个用户来办理业务，每个用户就是一个来自外部的请求线程
        try{
            for(int i = 1; i <= 20; i++){
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
                // 暂停一会
//                try{ TimeUnit.MILLISECONDS.sleep(200);}catch(InterruptedException e){e.printStackTrace();}
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            threadPool.shutdown();
        }
    }
}
