package com.study.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author harry
 * @create 2020-06-28 18:05
 * @Version 1.0
 */

class MyData{
//    int number = 0;
volatile int number = 0; // volatile 增加主线程和子线程之间的可见性，没有原子性，禁止指令重排

    public void addT060(){
        this.number = 60;
    }

    public void addPlusPlus(){   // synchronized 同步
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    public void addMyAtomic(){
        atomicInteger.getAndIncrement();
    }
}


/**
 * 1 验证volatile的可见性
 *  1.1 假如 int number = 0; number变量之前根本没有添加volatile关键词修饰
 *  1.2 添加了volatile，可以解决可见性问题。
 *
 * 2 验证volatile 不保证原子性
 *  2.1 原子性指的是什么
 *      不可分割，完整性，也即某个线程正在做某个具体业务时，中间不可以被加塞或者被分割。需要整体完整
 *      要么成功，要么同时失败。
 *  2.2 volatile不保证原子性的案例演示
 *
 *  2.3 why
 *
 *  2.4 如何解决原子性？
 *      *加sync
 *      *使用我们的juc 下的AtomicInteger
 */
public class VolatileDemo {

    public static void main(String[] args){

        MyData myData = new MyData();
        for(int i = 1; i<= 20; i++){
            new Thread(() -> {
                for(int j = 1; j <= 1000; j++){
                    myData.addPlusPlus();      // 不保证原子性
                    myData.addMyAtomic();      // 保证原子性
                }
            }, String.valueOf(i)).start();
        }
        // 需要等待上面20个线程都全部计算完成后，再用main线程取得最终的结果值看是多少？
        while (Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + "\t int type, finally number value: "+ myData.number);
        System.out.println(Thread.currentThread().getName() + "\t AtomicInteger type, finally number value: "+ myData.atomicInteger);

    }

    // volatile 可以保证可见性，及时通知其它线程，主物理内存的值已经被修改
    public static void seeOkByVolatile(String[] args){

        MyData myData = new MyData(); // 资源类

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            // 暂停一会线程
            try{TimeUnit.SECONDS.sleep(3);}catch (InterruptedException e){e.printStackTrace();}
            myData.addT060();
            System.out.println(Thread.currentThread().getName() + "\t  updated number value: " + myData.number);

        }, "AAA").start();

        // 第二个线程,就是main线程
        while(myData.number == 0){
            // main 线程就一直在这里等待循环，直到number值不在等于零。
        }

        System.out.println(Thread.currentThread().getName() + "\t mission is over， main get number value : "+ myData.number);
    }
}
