import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        Date start = Date.from(now.plusMinutes(-10).atZone(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(start);
        System.out.println(end);
    }
}
