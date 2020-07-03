package com.study.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author harry
 * @create 2020-07-01 11:34
 * @Version 1.0
 *
 * 可重入锁（递归锁）
 * ReentrantLock/Synchronized 就是一个典型的可重入锁  --> 最大的作用是避免死锁
 */
public class ReentrantLockDemo {

    public static void main(String[] args) {
        Mobile mobile = new Mobile();
        new Thread(mobile).start();
        new Thread(mobile).start();
    }

    public static void testmain(String[] args){
        Phone phone = new Phone();
        new Thread(() -> {
            try{
                phone.sendSMS();
            }catch (Exception e){
                e.printStackTrace();
            }
        },"Thread 1").start();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Thread 2").start();
    }
}

class Phone{
    public synchronized void sendSMS() throws Exception{
        System.out.println(Thread.currentThread().getName() + "\t ----invoked sendSMS()");
        Thread.sleep(3000);
        sendEmails();
    }

    public synchronized void sendEmails()throws Exception{
        System.out.println(Thread.currentThread().getName() + "\t ++++++++++invoded sendEmail()");
    }
}

class Mobile implements Runnable{
    Lock lock = new ReentrantLock();
    @Override
    public void run(){
        get();
    }

    public void get(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t invoke get(()");
            set();
        }finally {
            lock.unlock();
        }
    }

    public void set(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t invoke set(()");
        }finally {
            lock.unlock();
        }
    }
}
