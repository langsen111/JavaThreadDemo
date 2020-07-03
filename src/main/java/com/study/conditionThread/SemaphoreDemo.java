package com.study.conditionThread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author harry
 * @create 2020-07-01 17:25
 * @Version 1.0
 * Semaphore信号量
 * 可以代替Synchronize和Lock
 *
 * 信号量主要有两个目的，一个用于多个共享资源的互斥作用，另一个用于并发线程数的控制
 *
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3); // 模拟三个停车位
        for(int i = 1; i <= 6; i ++){  // 模拟6部汽车
            new Thread(()-> {
                try{
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "\t 抢到车位");
                    try{
                        TimeUnit.SECONDS.sleep(3); // 停车3s
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "\t 停车3s后离开车位");
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            },"Car "+ i).start();
        }
    }
}
