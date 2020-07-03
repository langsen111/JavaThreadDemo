package com.study.lock;

import sun.jvm.hotspot.memory.LoaderConstraintEntry;

import java.util.concurrent.TimeUnit;

/**
 * @author harry
 * @create 2020-07-03 16:29
 * @Version 1.0
 *
 * 死锁是指两个或两个以上的进程在执行过程中，
 * 因争夺资源而造成的一种互相等待的现象，
 * 若无外力干涉那它们都将无法推进下去。
 */
public class DeadLockDemo {

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldLockThread(lockA, lockB), "ThreadAAA").start();
        new Thread(new HoldLockThread(lockB, lockA), "ThreadBBB").start();

        /**
         * linux   ps -ef|grep xxxx
         * window下的java运行程序  也有类似的查看进程的命令，但是目前我们需要查看的只是java
         *          jps = java ps     jps -l
         */
    }
}

class HoldLockThread implements  Runnable{

    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA, String lockB){
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run(){
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName() + "\t 自己持有： " + lockA + "\t 尝试获取: "+lockB);
            try{
                TimeUnit.SECONDS.sleep(2);}catch(InterruptedException e){e.printStackTrace();}

            synchronized (lockB){
                System.out.println(Thread.currentThread().getName() + "\t 自己持有： " + lockB + "\t 尝试获取: "+lockA);
            }
        }
    }

}
