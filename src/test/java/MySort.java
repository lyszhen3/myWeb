import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by pc on 2017-08-12.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class MySort {


    public synchronized void test(String name){
        while(name.equals("3")){

        }
        sout();
    }
    public void sout(){
        System.out.println("hello");

    }

    public static void main(String[] args) {
        MySort sort = new MySort();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                System.out.print(name);
                sort.test(name);
            }
        };
        for(int i=0;i<5;i++){
            Thread thread = new Thread(run);
            thread.setName(i+"");
            thread.start();
        }
    }
}
