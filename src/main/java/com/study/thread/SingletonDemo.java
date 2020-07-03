package com.study.thread;

/**
 * @author harry
 * @create 2020-06-29 17:34
 * @Version 1.0
 * 单机版下的单例模式     DCL双端检锁机制，+ volatile禁止指令重排
 */
public class SingletonDemo {

    private static volatile SingletonDemo instance = null;   // 加 volatile 禁止指令重排

    private SingletonDemo(){
        System.out.println(Thread.currentThread().getName() + "\t my is gouzaofangfan singletonDemo()");
    }


    // DCL( Doouble Check Lock 双端检锁机制）
    public static SingletonDemo getInstance(){
        if(instance == null){
            synchronized (SingletonDemo.class){     // 加锁，前后都判断代码块      ，，不能阻止指令重排
                if(instance == null){
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args){

        // 单线程（main 线程的操作动作。。。)
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());

        // 并发多线程后，情况发生了很大的变化
        for(int i = 1; i <= 10; i++){
            new Thread(() ->{
                SingletonDemo.getInstance();
            }, String.valueOf(i)).start();
        }
    }
}
