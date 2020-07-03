package com.study.queue;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author harry
 * @create 2020-07-01 17:51
 * @Version 1.0
 *
 *  1 队列
 *
 *
 *  2 阻塞队列
 *      2.1 阻塞队列有没有好的一面
 *
 *      2.2 不得不阻塞，如何管理
 */
public class BlockingQueueDemo {

    public static void main(String[] args) throws Exception{
        /*

         */
//        List list = null;
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
//        System.out.println(blockingQueue.add("a"));      //插入成功true，失败走异常
//        System.out.println(blockingQueue.add("b"));
//        System.out.println(blockingQueue.add("c"));
//
//        System.out.println(blockingQueue.element());  // 检查队首元素是什么
//
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue);
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());


//        System.out.println(blockingQueue.offer("a"));    // 插入成功true，失败false      特殊值
//        System.out.println(blockingQueue.offer("b"));              //offer(e) 特殊值            offer（e, time, uint) 超时，
//        System.out.println(blockingQueue.offer("c"));
//        System.out.println(blockingQueue.offer("x", 2L, TimeUnit.SECONDS));
//
//        System.out.println(blockingQueue.peek()); // 队首元素
//
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());


//        blockingQueue.put("a");            // 一直阻塞： 当阻塞队满时，生产者继续往队列中put，队列会一直阻塞线程，直到put数据响应中断退出
//        blockingQueue.put("a");            // 当阻塞为空时，消费者试图从队列take元素，队列会一直阻塞消费者线程，直到线程知道队列可用。
//        blockingQueue.put("a");
//        System.out.println(blockingQueue);
//        System.out.println("===========");
//
//        System.out.println();
//        blockingQueue.take();
//        blockingQueue.put("x");
//
//        System.out.println(blockingQueue);
//        blockingQueue.take();
//        blockingQueue.take();
//        blockingQueue.take();
//        blockingQueue.take();
//        System.out.println(blockingQueue);
//        blockingQueue.put("a");
//        System.out.println(blockingQueue);
    }

}
