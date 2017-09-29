package com.testbean;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import static org.junit.Assert.assertEquals;


/**
 * Created by pc on 2017-03-27.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
//复制拷贝属性 仅限于有get set方法
public class Cia {
    private String sex;
    private String name;
    public static Map<String,PropertyDescriptor> descriptorMap;


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        Cia ca=new Cia();
        Student stu=new Student();
        stu.name="lin";
        stu.setSex("man");

      //  BeanUtils.copyProperties(stu,ca);
//
        copyValues(stu,ca);
        System.out.println(ca.getName());

    }


    public static void copyValues(Object source, Object target){
        Class<?> sClazz=source.getClass();
        Class<?> tClazz=target.getClass();
        putMap(sClazz);
        try {
            BeanInfo info= Introspector.getBeanInfo(tClazz);
            PropertyDescriptor[] pds=info.getPropertyDescriptors();
            for(PropertyDescriptor pd:pds){

                if(!pd.getName().equals("class")){
                    PropertyDescriptor sourcePd=descriptorMap.get(pd.getName());
                    if(sourcePd!=null) {
                        Method readMethod = sourcePd.getReadMethod();
                        Object value = readMethod.invoke(source);
                        Method writeMethod = pd.getWriteMethod();

                        writeMethod.invoke(target, value);
                    }
                }
            }

        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }


    public static void putMap(Class<?> beanclass){
        BeanInfo info= null;
        try {
            info = Introspector.getBeanInfo(beanclass);
            PropertyDescriptor[] pds=info.getPropertyDescriptors();
            descriptorMap=new LinkedHashMap<String, PropertyDescriptor>();
            for(PropertyDescriptor pd:pds){
                if(!pd.getName().equals("class")){
                    descriptorMap.put(pd.getName(),pd);
                }

            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }






    }
    @Test
    public void TestT1() throws Throwable {

         Object x, y;
        String s;
        int i;
        MethodType mt;
        MethodHandle mh;
        MethodHandles.Lookup lookup = MethodHandles.lookup();
// mt is (char,char)String
        mt = MethodType.methodType(String.class, char.class, char.class);
        mh = lookup.findVirtual(String.class, "replace", mt);
        s = (String) mh.invokeExact("daddy", 'd', 'n');
// invokeExact(Ljava/lang/String;CC)Ljava/lang/String;
        assertEquals(s, "nanny");
// weakly typed invocation (using MHs.invoke)
        s = (String) mh.invokeWithArguments("sappy", 'p', 'v');
        assertEquals(s, "savvy");
// mt is (Object[])List
        mt = MethodType.methodType(java.util.List.class, Object[].class);
        mh = lookup.findStatic(java.util.Arrays.class, "asList", mt);
        assert (mh.isVarargsCollector());
        x = mh.invoke("one", "two");
// invoke(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
        assertEquals(x, java.util.Arrays.asList("one", "two"));
// mt is (Object,Object,Object)Object
        mt = MethodType.genericMethodType(3);
        mh = mh.asType(mt);
        x = mh.invokeExact((Object) 1, (Object) 2, (Object) 3);
// invokeExact(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        assertEquals(x, java.util.Arrays.asList(1, 2, 3));
// mt is ()int
        mt = MethodType.methodType(int.class);
        mh = lookup.findVirtual(java.util.List.class, "size", mt);
        i = (int) mh.invokeExact(java.util.Arrays.asList(1, 2, 3));
// invokeExact(Ljava/util/List;)I
        assert (i == 3);
        mt = MethodType.methodType(void.class, String.class);
        mh = lookup.findVirtual(java.io.PrintStream.class, "println", mt);
        mh.invokeExact(System.out, "Hello, world.");
// invokeExact(Ljava/io/PrintStream;Ljava/lang/String;)V

    }
}
