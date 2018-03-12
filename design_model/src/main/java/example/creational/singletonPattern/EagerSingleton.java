package example.creational.singletonPattern;

/**
 * 饿汉模式
 */
class EagerSingleton {
    private static final EagerSingleton instance = new EagerSingleton();   
    private EagerSingleton() { }   

    public static EagerSingleton getInstance() {  
        return instance;   
    }     
}