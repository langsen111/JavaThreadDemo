package com.study.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author harry
 * @create 2020-07-02 18:22
 * @Version 1.0
 *
 *  volatile/CAS/atomicInteger/BlockQueue/线程交互/原子引用
 */

class MyResource{
    private volatile boolean FLAG = true; // 默认开启，进行生产+消费
    private AtomicInteger atomicInteger = new AtomicInteger();

    BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    // 生产
    public void myProd()throws Exception{
        String data = null;
        boolean retValue;
        while (FLAG){
            data = atomicInteger.incrementAndGet()+ "";
            retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS); // 工作中用这个
            if(retValue){
                System.out.println(Thread.currentThread().getName() + "\t 插入队列 "+data + "成功");
            }else{
                System.out.println(Thread.currentThread().getName() + "\t 插入队列 "+data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "\t 大老板叫停，表示FLAG=false，生产动作结束");
    }


    // 消费
    public void myConsumer()throws Exception{
        String result  = null;
        while(FLAG){
            result=  blockingQueue.poll(2L, TimeUnit.SECONDS);  //2s等不到就不取了
            if(null == result || result.equalsIgnoreCase("")){
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t 超过2s，没有取到蛋糕，消费退出");
                System.out.println();
                System.out.println();
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 消费队列" + result + "成功");
        }
    }

    public void stop() throws Exception{
        this.FLAG = false;
    }
}

public class ProdConsumer_BLockQueueDemo {
    public static void main(String[] args) {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 生产线程启动");
            try{
                myResource.myProd();
            }catch(Exception e){
                e.printStackTrace();
            }
        }, "Prod").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 消费线程启动");
            try{
                myResource.myConsumer();
                System.out.println();
                System.out.println();
            }catch(Exception e){
                e.printStackTrace();
            }
        }, "Cousumer").start();

        try{TimeUnit.SECONDS.sleep(5);}catch(InterruptedException e){e.printStackTrace();}
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("5s后main叫停，线程结束");
        try{
            myResource.stop();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
