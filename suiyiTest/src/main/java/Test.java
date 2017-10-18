import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
        TimeUnit microseconds = TimeUnit.MILLISECONDS;
        System.out.println(microseconds);
        long l = System.currentTimeMillis();
        System.out.println(l);
    }
}
