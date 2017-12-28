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
        Hello hello = new Hello();
        hello.r1.run();
        hello.r2.run();
    }

}
