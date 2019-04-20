package example.designpattern.behavioral.iterator;

import java.util.List;

//商品数据类：具体聚合类
class ProductList extends AbstractObjectList {  
    public ProductList(List products) {
        super(products);  
    }  

    //实现创建迭代器对象的具体工厂方法
    @Override
    public AbstractIterator createIterator() {  
        return new ProductIterator(this);  
    }  
}