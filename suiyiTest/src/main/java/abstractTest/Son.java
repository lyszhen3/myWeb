package abstractTest;

/**
 * Created by pc on 2017/9/29.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class Son  extends Human{
    private Long id;
    private String name;
    public Son(Long id,String name){
        this.id = id ;
        this.name = name;
    }
    public Son(){

    }
    public static void main(String[] args) {
        Human h = new Son(11L,"JIA");
        Family f = new Family();
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
