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
    private Long id;
    private String name;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
