package com.study;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class MyThread1 implements Runnable{         // Runalbe接口没有返回值； 不抛异常
    @Override
    public void run(){         // 方法不一样，一个run（），一个call（）

    }
}

class MyThread implements Callable<Integer>{     // Callable有返回值； 会抛异常
    @Override
    public Integer call()throws Exception{
        System.out.println("*************come in callable");
        return  1024;
    }
}

/**
 * @author harry
 * @create 2020-07-03 10:41
 * @Version 1.0
 *
 * 多线程中，第3种获得多线程的方式
 */
public class CallableDemo {
    public static void main(String[] args) throws  InterruptedException, ExecutionException {

        // 两个线程，一个main线程，一个是AA futuretask


//        FutureTask(Callable<V> callable)
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());

        new Thread(futureTask, "AAA").start();
        new Thread(futureTask, "BBB").start();

        System.out.println(Thread.currentThread().getName() + "**********************");
        int result01 = 100;

//        while(!futureTask.isDone()){
//
//        }
        int result02 = futureTask.get();   // 建议放在最后// 要求获得Callable线程的计算结果，如果没有计算完成就要去强求，会导致堵塞，直到计算完成


        System.out.println("********result: "+(result01 + result02));
    }
}


