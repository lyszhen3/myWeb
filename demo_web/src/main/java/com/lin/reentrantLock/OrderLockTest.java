package com.lin.reentrantLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by pc on 2017-10-18.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class OrderLockTest {


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                try {
                    write("111");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();

    }

    public static void read(String key) throws InterruptedException {
        OrderLock lock = null;
        try {
            lock = OrderLockUtil.getLock(key);
        } catch (OrderLockUtil.MaximumLockCountExceeded maximumLockCountExceeded) {
            System.out.println("订单并发操作太高了吧。。");
        }
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "读操作开始");
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + "读操作结束");
        } finally {
            lock.readLock().unlock();
            OrderLockUtil.returnLock(key);
        }


    }

    public static void write(String key) throws InterruptedException {
        OrderLock lock = null;
        try {
            lock = OrderLockUtil.getLock(key);
        } catch (OrderLockUtil.MaximumLockCountExceeded maximumLockCountExceeded) {
            System.out.println("订单并发操作太高了吧。。");
        }
        System.out.println(lock.getNum());
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "写操作开始");
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + "写操作结束");
        } finally {
            lock.writeLock().unlock();
            OrderLockUtil.returnLock(key);
            System.out.println(lock.getNum());
        }

    }
}
