package example.designpattern.behavioral.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Client {
    public static void main(String args[]) {  
        List products = new ArrayList();
        products.add("倚天剑");  
        products.add("屠龙刀");  
        products.add("断肠草");  
        products.add("葵花宝典");  
        products.add("四十二章经");
        Iterator iterator1 = products.iterator();
        AbstractObjectList list;
        AbstractIterator iterator;  

        list = new ProductList(products); //创建聚合对象  
        iterator = list.createIterator();   //创建迭代器对象  

        System.out.println("正向遍历：");      
        while(!iterator.isLast()) {  
            System.out.print(iterator.getNextItem() + "，");  
            iterator.next();  
        }  
        System.out.println();  
        System.out.println("-----------------------------");  
        System.out.println("逆向遍历：");  
        while(!iterator.isFirst()) {  
            System.out.print(iterator.getPreviousItem() + "，");  
            iterator.previous();  
        }  
    }  
}