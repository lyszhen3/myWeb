package com.proxy;

/**
 * Created by pc on 2017-07-12.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class Hello implements IHello{
    @Override
    public void sayHello() {
        System.out.println("hello");
    }
}
