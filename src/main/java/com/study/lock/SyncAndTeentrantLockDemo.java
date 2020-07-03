package com.study.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author harry
 * @create 2020-07-02 17:43
 * @Version 1.0
 *
 * 1.原始构成
 * synchronized的关键字属于jvm，底层通过monitor对象来完成
 * Lock 是具体类，是api层面的锁（java.util.)
 *
 *
 * synchronized 和 lock 区别
 * =======lock 可绑定多个条件======
 * 对线程之间按顺序调用，实现A>B>C三个线程启动，要求如下：
 * AA 打印5次， BB 打印10次， CC 打印15次
 * 紧接着
 * AA 打印5次， BB 打印10次， CC 打印15次
 * ...
 * 操作十轮
 */
public class SyncAndTeentrantLockDemo {

    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(() -> {
            for(int i = 1; i <= 10; i++){
                 shareData.print5();
            }
        }, "A").start();

        new Thread(() -> {
            for(int i = 1; i <= 10; i++){
                shareData.print10();
            }
        }, "B").start();

        new Thread(() -> {
            for(int i = 1; i <= 10; i++){
                shareData.print15();
            }
        }, "C").start();
    }
}

class ShareData{
    private int number = 1; // A=1, B=2, C=3
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();    // 精确唤醒   锁绑定多个条件Condition，ReentrantLock用来实现分组唤醒需要唤醒的线程，
    private Condition condition2 = lock.newCondition();    //           而不像synchronized要么随机唤醒一个线程，要么全部都唤醒
    private Condition condition3 = lock.newCondition();

    public void print5(){
        lock.lock();
        try{
            // 1 判断
            while(number != 1){
                condition1.await();
            }
            // 2 干活
            for(int i = 1; i <= 5; i++){
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 3 通知    a线程通知b线程
            number = 2;
            condition2.signal();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    public void print10(){
        lock.lock();
        try{
            while(number != 2){
                condition2.await();
            }
            for(int i = 1; i <= 10; i++){
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            number = 3;
            condition3.signal();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    public void print15(){
        lock.lock();
        try{
            while(number != 3){
                condition3.await();
            }
            for(int i = 1; i <= 15; i++){
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            number = 1;
            condition1.signal();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }


}
