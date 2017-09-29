package com.testclassNotLoad;

/**
 * Created by pc on 2017-05-18.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class SuperClass {
    static {
        System.out.println("SuperClass init");
    }
    public static int value=12;
}
