import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by pc on 2017-10-11.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class Test {
    public static void main(String[] args) {
       List<String > values = Arrays.asList("E","A","B","C","d");

        Collections.sort(values, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
               int i = o1.compareToIgnoreCase(o2);
                return i;
            }
        });

        values.sort(String::compareToIgnoreCase);
        values.forEach(System.out::println);
        UUID uuid = UUID.fromString("507-211-43-45-23");
        System.out.println(uuid);
        Function ff = new Function() {
            @Override
            public Object apply(Object o) {
                return null;
            }
        };
        Comparator<String> a;
        a = (s1,s2)->s1.compareToIgnoreCase(s2);
        List<String> slist = new ArrayList<>();

    }
}
