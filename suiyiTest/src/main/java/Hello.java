import java.util.Map;

/**
 * Created by lys on 11/16/2017.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Hello {
    //java8新特性能直接this 访问的是当前类
    Runnable r1 = ()-> System.out.println(this);
    Runnable r2 = ()-> System.out.println(this.toString());
    //匿名内部类访问的是内部类
    Runnable r3 = new Runnable() {
        @Override
        public void run() {
            System.out.println(this);
        }
    };
    Runnable r4 = new Runnable() {
        @Override
        public void run() {
            System.out.println(this.toString());
        }
    };

    public String toString(){
        return "hello world";
    }

    public static void main(String[] args) {
        Hello hello = new Hello();
        hello.r1.run();
        hello.r2.run();
        hello.r3.run();

        hello.r4.run();

    }

}
