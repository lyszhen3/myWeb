package interfaces;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by lys on 1/17/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class AbstractClass extends BeanClass implements  InterfaceMain{


    public static void main(String[] args) {
        Class<? super AbstractClass> superclass = AbstractClass.class.getSuperclass();

        Method[] methods = superclass.getDeclaredMethods();
        for (Method method : methods) {
            boolean aStatic = Modifier.isStatic(method.getModifiers());
            System.out.println(method.getName()+"-static-:"+aStatic);

        }
        int[] a = {1,2,3};
        int[] b = new int[2];
        System.arraycopy(a,0,b,0,2);
        for (int i : b) {
            System.out.println(i);
        }
        System.out.println(System.nanoTime());//毫微秒
        System.out.println(System.currentTimeMillis());

        System.out.println(superclass.getName());
    }
}
