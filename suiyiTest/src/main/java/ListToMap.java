import abstractTest.Son;
import abstractTest.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by pc on 2017-10-09.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class ListToMap {
    public static void main(String[] args) {
        List<Son> list = new ArrayList<>();
        Son son = new Son();
        son.setId(1L);
        son.setName("我的");
        Son son2 = new Son();
        son2.setName("你的");
        son2.setId(2L);
        Son son3 = new Son();
        son3.setId(1L);
        son3.setName("他的");
        list.add(son);
        list.add(son2);
        list.add(son3);

        Map<Long, Son> collect = list.stream().collect(Collectors.toMap(Son::getId, Function.identity(),(key1, key2)->key2));
        System.out.println(collect);
    }



}
