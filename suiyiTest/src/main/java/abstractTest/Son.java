package abstractTest;

/**
 * Created by pc on 2017/9/29.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class Son  {
    public static void main(String[] args) {
        String id = "888";
        String name = "lll";
        Student student = new Student(id,name) {
            @Override
            public void sayHello() {
                System.out.println(this.getId()+"你好啊");
            }
        };
        student.sayHello();


    }
}
