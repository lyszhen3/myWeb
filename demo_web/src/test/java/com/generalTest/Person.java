package com.generalTest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by pc on 2017-06-16.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class Person<T,E> {
    private Class<T> clazz;
    private Class<E> clazz2;
    public Person(){
        clazz = (Class<T>)((ParameterizedType) super.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        clazz2=(Class<E>)((ParameterizedType) super.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Class<E> getClazz2() {
        return clazz2;
    }

    public void setClazz2(Class<E> clazz2) {
        this.clazz2 = clazz2;
    }
}
