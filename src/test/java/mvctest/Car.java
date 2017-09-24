package mvctest;

/**
 * Created by lys on 2017-09-23.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class Car {
    private String name;

    public Car(String name){
        this.name = name;
    }
    public Car(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
