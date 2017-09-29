package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by pc on 2017-04-20.
 *死锁
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class JconsoleThread {

    public static void createBusyThread(){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true);
            }
        },"testBusyThread");
        thread.start();

    }

    public static void createLockThread(final Object lock){

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        },"testLockThread");
        thread.start();

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        createBusyThread();
        br.readLine();
        Object obj=new Object();
        createLockThread(obj);

    }

    static class SynAddRunnable implements Runnable{
        int a,b;

        public SynAddRunnable(int a,int b){
            this.a=a;
            this.b=b;
        }
        @Override
        public void run() {
            synchronized (Integer.valueOf(a)){
                synchronized (Integer.valueOf(b)){
                    System.out.println(a+b+":"+Thread.currentThread().getName());
                }

            }

        }

        public static void main(String[] args) {
            for(int i=0;i<100;i++){
                new Thread(new SynAddRunnable(1,2),"我的1:第"+i+"个").start();
                new Thread(new SynAddRunnable(2,1),"我的2:第"+i+"个").start();
            }
        }
    }
}
