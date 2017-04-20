package com;

/**
 * Created by pc on 2017-04-20.
 *
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



    }
}
