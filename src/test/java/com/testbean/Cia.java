package com.testbean;

import org.springframework.beans.BeanUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;


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

}
