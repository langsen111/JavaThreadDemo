package com.study.conditionThread;

import java.sql.Time;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author harry
 * @create 2020-07-01 16:16
 * @Version 1.0
 *
 * CountDownLatch(火箭发射倒计时）
 * 主要有两个方法，当一个或多个线程调用await()方法时，调用线程会被阻塞。其它线程
 * 调用countDown（）方法会将计数器-1，当计数器的值变为0时，因调用await（）方法被阻塞的
 * 线程才会被唤醒，继续执行
 *
 * 设定一个等待数，所有线程执行了就调用countDown（）方法-1，直到值为0时才继续执行被阻塞的线程
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws  InterruptedException{
//        general();
        countDownLoatchTest();
//        System.out.println(Runtime.getRuntime().availableProcessors());  // 查看系统cpu数量

    }

    public static void general(){
        for(int i = 1; i <= 7; i++){
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"\t 上完自习，离开教室");
            },"Thread--->" + i).start();
        }
        while(Thread.activeCount() > 2){
            try{TimeUnit.SECONDS.sleep(2);}catch(InterruptedException e){e.printStackTrace();}
        }
        System.out.println(Thread.currentThread().getName()+"\t 班长最后走人");
    }

    public static void countDownLoatchTest() throws  InterruptedException{
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for(int i = 1; i <= 6; i ++){
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 被灭");
                countDownLatch.countDown();
            },CountryEnum.forEach_CountryEnum(i).getRetMessage()).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t ===== 秦统一");

    }
}
