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
    public static void main(String[] args) {
//        Integer[] ints1 = new Integer[]{99,1,2,1,3,6,5,9,7,98};
//        for(int i = 1;i<ints1.length;i++){
//            for(int j= 0;j<ints1.length-i;j++){
//
//                if(ints1[j+1]<ints1[j]) {
//                    int temp = ints1[j + 1];
//                    ints1[j+1] = ints1[j];
//                    ints1[j] = temp;
//                }
//            }
//        }
//        for(int i:ints1){
//            System.out.println(i);
//        }
//        List<Integer> integers = Arrays.asList(ints1);
//        Collections.sort(integers);
        new Concrete();
    }
}
class Base{
    public  Integer i =2;
    public Base(){
        display();
    }
    public void display(){
        System.out.println(i);
    }
}
class Concrete extends Base{
    public  Integer i =22;
    public Concrete(){
        i = 222;
    }
    @Override
    public void display(){
        System.out.println(i);
    }
}
