package com.study.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author harry
 * @create 2020-06-30 18:34
 * @Version 1.0
 *
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行
 * 但是
 * 如果有一个线程想去写共享资源类，就不应该再有其它线程可以对该资源进行读或写
 *
 * 总结：
 *      读 - 读 能共存
 *      读 - 写 不能共存
 *      写 - 写 不能共存
 *
 *      写操作：原子 + 独占    整个过程必须是一个完整的统一体，中间不许被分割和打断
 */
public class ReadwriteLockDemo {

    public static void main(String[] args){

        Mycache mycache = new Mycache();

        for(int i = 1; i <= 5; i ++){
            final int tempInt = i;
            new Thread(()->{
                mycache.put(tempInt + "", tempInt+"");
            }, String.valueOf(i)).start();
        }

        for(int i = 1; i <= 5; i ++){
            final int tempInt = i;
            new Thread(()->{
                mycache.get(tempInt + "");
            }, String.valueOf(i)).start();
        }
    }
}

class Mycache { // 资源类

    private volatile Map<String, Object> map = new HashMap<>();
//    private Lock lock = new ReentrantLock();
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void put(String key, Object value){

        rwLock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t 正在写入 : "+ key);
            // 暂停一会线程
            try{TimeUnit.MILLISECONDS.sleep(300);}catch(InterruptedException e){e.printStackTrace();}
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成 : ");

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            rwLock.writeLock().unlock();
        }
    }

    public void get(String key){

        rwLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t 正在读取 : "+ key);
            // 暂停一会线程
            try{TimeUnit.MILLISECONDS.sleep(300);}catch(InterruptedException e){e.printStackTrace();}
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取完成 : " + result);

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            rwLock.writeLock().unlock();
        }
    }

    // 手写缓存
//    public void clearMap(){
//        map.clear();
//    }
}

