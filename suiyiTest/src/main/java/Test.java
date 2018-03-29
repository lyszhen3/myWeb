import abstractTest.Italk;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by pc on 2017-10-11.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class Test {

    @org.junit.Test
    public void test(){
       List<Integer> integers = new ArrayList<>();
       List<Integer> integerList = new ArrayList<>();
       for (int i = 0;i<100;i++){
           integers.add(i);
       }
       integers.forEach(i->integerList.add(i));
        System.out.println(integerList.size());
        System.out.println(0x11);
    }


}
