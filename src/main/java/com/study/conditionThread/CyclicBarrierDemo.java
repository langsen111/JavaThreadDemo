package com.study.conditionThread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author harry
 * @create 2020-07-01 17:12
 * @Version 1.0
 *
 * 可循环（Cyclic）使用的屏障。让一组线程到达一个屏障（也可叫同步点）时被阻塞，知道最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续干活，线程进入屏障通过CycliBarrier的await()方法
 *
 * 线程增加到多少，开始干活
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        cyclicBarrierTest();
    }

    public static void cyclicBarrierTest(){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () ->{
            System.out.println("======召唤神龙=======");
        });

        for(int i = 1; i <= 7; i++){
            final int tempInt = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "\t收集到第"+ tempInt + "颗龙珠");
                try{
                    cyclicBarrier.await();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }catch (BrokenBarrierException e){
                    e.printStackTrace();
                }
                }, ""+i).start();
        }
    }
}
