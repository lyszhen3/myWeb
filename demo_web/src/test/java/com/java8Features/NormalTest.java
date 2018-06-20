package com.java8Features;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/9/22.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class NormalTest {

    public static void main(String[] args) {
//        List<String> lists  = new ArrayList<>();
//        for (int i = 0; i < 1000; i++) {
//            lists.add(i+"");
//        }
//        lists.stream().parallel().forEach(System.out::println);
        //使用流还是会存在精度问题
        List<Pmoney> list = new ArrayList<>();
        list.add(new Pmoney("LIN",0.11));
        list.add(new Pmoney("ZHANG",2001299.32));
        list.add(new Pmoney("WANG",0.999));
        double sum = list.stream().mapToDouble(Pmoney::getMoney).sum();
        System.out.println(sum);
        double d1 = 0.11;
        double d2 = 2001299.32;
        System.out.println(d1+d2);
    }

}
