import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * Created by pc on 2017/9/25.
 * 随便测试类
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class STest {

    public static void main(String[] args) {
        DateTimeFormatterBuilder bd = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR)
                .appendLiteral("_")
                .appendValue(ChronoField.MONTH_OF_YEAR)
                .appendLiteral("_")

                .appendValue(ChronoField.DAY_OF_MONTH);
        DateTimeFormatter dateTimeFormatter = bd.toFormatter();
        LocalDateTime currentTime = LocalDateTime.now();
        String format = dateTimeFormatter.format(currentTime);
        System.out.println(format);

    }

}
