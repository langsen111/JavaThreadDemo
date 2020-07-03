package com.study.queue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author harry
 * @create 2020-07-02 16:12
 * @Version 1.0
 *  传统方式
 *  案例：一个初始值为零的变量，两个线程对其交替操作，一个加1一个减1，进行5轮
 *
 *  多线程步骤
 *  1 线程          操作（方法）          资源类
 *  2 判断          干活                 通知
 *  3 防止虚假唤醒机制 （多线程只能用while来判断，不用if）
 */
public class ProdConsumer_traditionDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        for(int i = 1; i <= 5; i++){
            new Thread(() -> {
                try{
                    shareData.increment();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }, "Productor-A-" + i).start();
        }

        for(int i = 1; i <= 5; i++){
            new Thread(() -> {
                try{
                    shareData.decrement();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }, "Consumer-A-" + i).start();
        }

        for(int i = 1; i <= 5; i++){
            new Thread(() -> {
                try{
                    shareData.increment();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }, "Productor-B-" + i).start();
        }

        for(int i = 1; i <= 5; i++){
            new Thread(() -> {
                try{
                    shareData.decrement();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }, "Consumer-B-" + i).start();
        }
    }
}

// 线程操作资源类
class ShareData{  // 资源类
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws Exception{
        lock.lock();
        try{
            // 1 判断
            while(number != 0){
                // 等待不能生产
                condition.await();
            }
            // 2.干活
            number++;
            System.out.println(Thread.currentThread().getName() + "\t"  + number);
            // 3.通知唤醒
            condition.signalAll();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    public void decrement() throws Exception{
        lock.lock();
        try{
            // 1 判断
            while(number == 0){
                // 等待不能消费
                condition.await();
            }
            // 2.消费
            number--;
            System.out.println(Thread.currentThread().getName() + "\t"  + number);
            // 3.通知
            condition.signalAll();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

}
