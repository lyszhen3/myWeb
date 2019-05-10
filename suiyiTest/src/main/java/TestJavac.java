import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by lys on 2019/2/11.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class TestJavac {
    public static void main(String[] args) throws Exception {
//		Main.main(new String[]{"D:\\mywork\\myWeb\\suiyiTest\\src\\main\\java\\ListToMap.java"});
        List<Integer> collect = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        System.out.println(collect);
        ;

    }

    public static void sayLoop() {
        throw new RuntimeException("wo");
    }
}
