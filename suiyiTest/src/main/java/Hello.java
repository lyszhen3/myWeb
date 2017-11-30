import java.util.Map;

/**
 * Created by lys on 11/16/2017.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Hello {
    Runnable r1 = ()-> System.out.println(this);
    Runnable r2 = ()-> System.out.println(this.toString());

    public String toString(){
        return "hello world";
    }

    public static void main(String[] args) {

    }
    public static boolean xx(Class<?> clazz){
        return byte[].class == clazz;
    }
}
