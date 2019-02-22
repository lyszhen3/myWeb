package io;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by lys on 2017-08-28.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class PPrint {
    public static String pformat(Collection<?> c){
        if(c.size() == 0){
            return "[]";
        }
        StringBuilder result = new StringBuilder("[");
        for (Object elem : c) {
            if(c.size()!=1) {
                result.append("\n");
            }
                result.append(elem);

        }
        if(c.size()!=1){
            result.append("\n");
        }
        result.append("]");
        return result.toString();
    }
    public static void pprint(Collection<?> c){
        System.out.println(pformat(c));
    }

    public static void pprint(Object[] c){
        System.out.println(Arrays.asList(c));
    }

}
