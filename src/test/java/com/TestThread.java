package com;

import org.junit.Test;

/**
 * Created by pc on 2017-02-16.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class TestThread {

    public synchronized void iamf1(){
        try {
            System.out.println("方法一线程的开始");
            Thread.sleep(5000);
            System.out.println("方法一线程的结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public synchronized void iamf2(){

        try {
            System.out.println("方法二线程的开始");
            Thread.sleep(5000);
            System.out.println("方法二线程的结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    class F1 implements Runnable{

        public void run() {
            iamf1();
        }
    }

    class F2 implements Runnable{

        public void run() {
            iamf2();
        }
    }
    //同一对象
    @Test
    public  void Test1() {
        TestThread t=new TestThread();
        F1 f1=t.new F1();
        F2 f2=t.new F2();
        Thread thread1=new Thread(f1);
        thread1.start();
        Thread thread2=new Thread(f2);
        thread2.start();
    }
    //不同对象
    public void Test2(){
        TestThread t=new TestThread();
        TestThread t2=new TestThread();
        F1 f1=t.new F1();
        F2 f2=t2.new F2();
        Thread thread1=new Thread(f1);
        thread1.start();
        Thread thread2=new Thread(f2);
        thread2.start();
    }

    public static void main(String[] args) {
        Thread thread1=new Thread(new String("1"));
        Thread thread=new Thread(new Runnable() {
            public void run() {

            }
        });
    }



}
