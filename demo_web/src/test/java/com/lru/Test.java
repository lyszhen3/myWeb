package com.lru;

import java.util.*;

/**
 * Created by pc on 2017-08-16.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class Test {
    public static void main(String[] args) {
        Map map  = new LinkedHashMap<>();
        List<String> list = new ArrayList<>();

        list.add("张春华");
        list.add("胡锦涛");
        list.add("习近平");
        Collections.sort(list);
        list.forEach(System.out::println);

    }

}
