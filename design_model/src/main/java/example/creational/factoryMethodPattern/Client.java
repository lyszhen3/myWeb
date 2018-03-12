package example.creational.factoryMethodPattern;

public class Client {
    public static void main(String args[]) {  
        LoggerFactory factory;  
        Logger logger;  
//        factory = new FileLoggerFactory(); //可引入配置文件实现
        factory = (LoggerFactory)XMLUtil.getBean();
        logger = factory.createLogger();  
        logger.writeLog();
    }
}