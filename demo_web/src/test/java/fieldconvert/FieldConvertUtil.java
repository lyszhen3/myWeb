package fieldconvert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import mvctest.Car;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lys on 3/14/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class FieldConvertUtil {
    private List<AbstractProcessor> abstractProcessors;
    private Object obj;
    public FieldConvertUtil(List<AbstractProcessor> abstractProcessors,Object obj){
        this.abstractProcessors = abstractProcessors;
        this.obj = obj;
    }

    public void convert(){
         Class<?> clazz = obj.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {

            for (AbstractProcessor abstractProcessor : abstractProcessors) {
             Annotation annotation;
               if (declaredField.getType().equals(abstractProcessor.fieldType) && (annotation = declaredField.getAnnotation(abstractProcessor.annotation)) != null) {
                String getMethodName = "get" + firstUpCase(declaredField.getName());
                String setMehotdName = "set" + firstUpCase(declaredField.getName());
                try {
                    Method getMethod = clazz.getDeclaredMethod(getMethodName);
                    Method setMethod = clazz.getDeclaredMethod(setMehotdName, abstractProcessor.fieldType);
                    Object oriValue =  getMethod.invoke(obj);
                    if (oriValue != null) {
                        Object convertValue =  abstractProcessor.convert(oriValue, annotation);
                        setMethod.invoke(obj, convertValue);
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    throw new RuntimeException(String.format("属性%s未找到get,set方法", declaredField.getName()));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    throw new RuntimeException(String.format("方法%s、%s invoke失败", getMethodName, setMehotdName));
                }

            }
            }

            //字段类型匹配


        }
    }


    private String firstUpCase(String ori) {
        StringBuilder builder;
        if (ori != null) {
            builder = new StringBuilder();
            builder.append(ori.substring(0, 1).toUpperCase()).append(ori.substring(1));
            return builder.toString();
        } else {
            return null;
        }
    }



}
