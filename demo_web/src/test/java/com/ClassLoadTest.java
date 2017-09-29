package com;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pc on 2017-05-26.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class ClassLoadTest {

    public static void main(String[] args) throws Exception{
        //测试不同类加载器实例化对象不同
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {

                try {
                    String fileName = name.substring(name.lastIndexOf(".")+1)+".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if(is == null){
                        return super.loadClass(name);
                    }
                    byte[] b=new byte[is.available()];
                    is.read(b);
                    return defineClass(name,b,0,b.length);

                } catch (IOException e) {
                   throw new ClassNotFoundException(name);
                }

            }
        };
        Object obj = classLoader.loadClass("com.ClassLoadTest").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof ClassLoadTest);



        //测试多线程类加载同步
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread() + "start");
            DeadLoopClass dlc = new DeadLoopClass();
            System.out.println(Thread.currentThread() + "run over");

        };
        Thread thread1=new Thread(runnable);
        Thread thread2=new Thread(runnable);

        thread1.start();
        thread2.start();

    }

    static class DeadLoopClass{
        static{
            if(true){
                System.out.println(Thread.currentThread()+"init DeadLoopClass");
                while(true){

                }

            }
        }


    }
}
