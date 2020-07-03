package com.study.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author harry
 * @create 2020-06-30 18:11
 * @Version 1.0
 *
 * 实现一个自旋锁
 * 自旋锁好处：循环比较获取知道成功为止，没有类似wait的阻塞。
 *
 * 通过CAS操作完成自旋锁，A线程先进来调用myLock方法自己持有锁5秒钟，B随后进来发现
 * 当前有线程持有锁，不是null，所以只能通过自旋等待，直到A释放锁后B随后抢到。
 */
public class SpinLockDemo {

    // 原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock(){
        Thread thread = Thread.currentThread(); // 当前进来的对象
        System.out.println(Thread.currentThread().getName() + "\t come in ");

        while(!atomicReference.compareAndSet(null, thread)){

        }
    }

    public void myUnLock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "\t invoked myUnLock() ");
    }

    // 线程操作资源类
    public static void main(String[] args){

        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(() -> {
            spinLockDemo.myLock();
            // 暂停一会线程
            try{TimeUnit.SECONDS.sleep(50);}catch(InterruptedException e){e.printStackTrace();}
            spinLockDemo.myUnLock();
        }, "AA").start();

        try{TimeUnit.SECONDS.sleep(1);}catch(InterruptedException e){e.printStackTrace();}

        new Thread(() -> {
            spinLockDemo.myLock();
            spinLockDemo.myUnLock();
        }, "BB").start();
    }
}
