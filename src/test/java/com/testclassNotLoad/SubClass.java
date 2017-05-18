package com.testclassNotLoad;

/**
 * Created by pc on 2017-05-18.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class SubClass extends SuperClass {
    static{
        System.out.println("subClass init");
    }
    public static final String HelowWorld="hello world";//final 编译时就会进入常量池
}
