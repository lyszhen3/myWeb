package com;

/**
 * Created by pc on 2017-02-13.
 *jvm 测试
 * -verbose:gc -Xms20m -Xmx20m -Xmn10m -XX:SurvivorRatio=8 -XX:+PrintGCDetails
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 *
 * ps -XX:-UseParallelGC新生代多线程 老年代单线程
 *    -XX:-UseParallelOldGC 新生老年都是多线程
 */
public class MyTest {

    public static final int _1MB=1024*1024;

    public static  void testAllocation(){
        byte[] allocation1,allocation2,allocation3,allocation4;
        allocation1=new byte[2*_1MB];
        allocation2=new byte[2*_1MB];
        allocation3=new byte[2*_1MB];
        allocation4=new byte[4*_1MB];
    }
    /**
     * -XX:MaxTenuringThreshold=1
     * -XX:+PrintTenuringDistribution
     * */
    public static  void testTenuringThreshold(){
        byte[] allocation1,allocation2,allocation3;
        allocation1=new byte[_1MB / 4];
        allocation2=new byte[4*_1MB];
        allocation3=new byte[4*_1MB];
        allocation3=null;
        allocation3=new byte[4*_1MB];
    }


    public static void main(String[] args) {
//        testAllocation();
        //testTenuringThreshold();
        testTenuringThreshold2();
    }

    /**
     * 相同年龄超过survivor一半空间 直接进入老年代
     * 注释掉allocation1和allocation2其中一个再对比。。
     */

    @SuppressWarnings("unused")
    public static void testTenuringThreshold2(){

        byte[] allocation1,allocation2,allocation3,allocation4;
        allocation1=new byte[_1MB / 4];
        allocation2=new byte[_1MB / 4];
       // allocation1+allocation2等于survivor一半内存
        allocation3=new byte[4*_1MB];
        allocation4=new byte[4*_1MB];
        allocation4=null;
        allocation4=new byte[4*_1MB];

    }
}
