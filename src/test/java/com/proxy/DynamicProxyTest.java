package com.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by pc on 2017-07-12.
 *动态代理测试
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class DynamicProxyTest {

    interface IHello{
        void sayHello();
        void sayByeBye();
    }
    static class Hello implements IHello{

        @Override
        public void sayHello() {
            System.out.println("hello world");
        }

        @Override
        public void sayByeBye() {
            System.out.println("bye bye");
        }
    }
    static class DynamicProxy implements InvocationHandler{

        Object originObj;
        Object bind(Object originObj){
            this.originObj = originObj;
             return Proxy.newProxyInstance(originObj.getClass().getClassLoader(),originObj.getClass().getInterfaces(),this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            System.out.println("welcome");
            Object invoke = method.invoke(originObj, args);
            System.out.println("over");
            return invoke;
        }
    }



    public static void main(String[] args) {


        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        IHello hello = (IHello) new DynamicProxy().bind(new Hello());
        hello.sayByeBye();
    }

}
