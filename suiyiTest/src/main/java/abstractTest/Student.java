package abstractTest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * Created by pc on 2017/9/28.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public abstract class Student {
    private String id;
    private String name;

    public Student(String id,String name){
        this.id = id;
        this.name = name;
    }
    public Student(){}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public abstract void sayHello();

}
