import java.io.File;

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
        String path = Thread.currentThread().getContextClassLoader().getResource("//").getPath();
        String newP = path+"../";
        File file = new File(newP);
        System.out.println(file.getPath());
    }
}
