package com.testbean;

import org.springframework.beans.BeanUtils;

/**
 * Created by pc on 2017-05-16.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class TestBeanCopy {
    public static void main(String[] args) {
        A1 a1=new A1() {
        };
        a1.setName("天");
        A2 a2=new A2() {
        };
        //BeanUtils.copyProperties(a1,a2);
        Cia.copyValues(a1,a2);
        System.out.println(a2.getName());

    }
}
