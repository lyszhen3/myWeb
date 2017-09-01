package com;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by pc on 2017-04-07.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class DeepCopy implements Cloneable,Serializable{

    private static final long serialVersionUID = -3430073996818420451L;
    protected Long name;
    protected List<Integer> integers;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        DeepCopy obj=null;
        obj=(DeepCopy)super.clone();
        obj.name=new Long(name);

        try {
            obj.integers= deepCopy(integers);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static AtomicInteger age;
    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException {
       DeepCopy t = new DeepCopy();
       t.name =5000L;
       Integer in_1=500;
       Integer in_2=600;
       t.integers=Arrays.asList(in_1,in_2);


       //Test t2 = (Test) t.clone();
        DeepCopy t2=deepClone(t);
        System.out.println(t==t2);


        System.out.println(t.integers.get(0)==t2.integers.get(0));

        System.out.println(t.name == t2.name);
    }
    public static<T>  List<T> deepCopy(List<T> list) throws IOException, ClassNotFoundException {
        List<T> array=null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oout= new ObjectOutputStream(out);
        oout.writeObject(list);
        ByteArrayInputStream in =new ByteArrayInputStream(out.toByteArray());
        ObjectInputStream oin= new ObjectInputStream(in);
        array = (List<T>)oin.readObject();
        return array;
    }

    public static<T>  T deepClone(T t) throws IOException, ClassNotFoundException {
        T tt=null;
         ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oout= new ObjectOutputStream(out);
        oout.writeObject(t);
        ByteArrayInputStream in =new ByteArrayInputStream(out.toByteArray());
        ObjectInputStream oin= new ObjectInputStream(in);
        tt=(T)oin.readObject();
        return tt;

    }



}