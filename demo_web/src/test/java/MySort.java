import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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
//        MySort sort = new MySort();
//        Runnable run = new Runnable() {
//            @Override
//            public void run() {
//                String name = Thread.currentThread().getName();
//                System.out.print(name);
//                sort.test(name);
//            }
//        };
//        for(int i=0;i<5;i++){
//            Thread thread = new Thread(run);
//            thread.setName(i+"");
//            thread.start();
//        }
        LinkedList<Integer> integers = new LinkedList<>();
        Long m = 100L;
        double v = m * .01;
        System.out.println(v);
        Class<?> clazz = MySort.class;
        System.out.println(clazz.getName());
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> mm = new HashMap<>();
        mm.put("10", new BigDecimal(1).setScale(2));
        mm.put("20", new BigDecimal(2).setScale(2));
        jsonObject.put("price", mm);
        String s = jsonObject.toJSONString();
        System.out.println(s);
    }
}
