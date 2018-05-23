package shen.wode;

import abstractTest.Human;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by lys on 11/17/2017.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Teacher extends Human implements Comparable<Teacher>{

    protected String name;

    protected int id;

    public void introduce(){
        Human human = new Human();
    }

    public static void main(String[] args) {
        List<Teacher> list = new ArrayList<>();
        Teacher teacher = new Teacher();
        teacher.id = 2;
        teacher.name = "卧槽";
        Teacher teacher1 = new Teacher();
        teacher1.id =3;
        teacher1.name="你耗";
        list.add(teacher);
        list.add(teacher1);
        Teacher add_teacher1 = new Teacher();
        add_teacher1.id = 1;
        add_teacher1.name = "怎么按不能";
        list.add(add_teacher1);
        //逆向排序

//        list.sort(Collections.reverseOrder(Teacher::compareTo));
        Collections.sort(list);
        list.forEach(tt-> System.out.println(tt.getId()));
        Optional<Teacher> release = getRelease(list, teacherEntry -> teacherEntry.getId() == 3);
        release.ifPresent(teacher2 -> System.out.println(teacher2.getName()));
    }


    private static Optional<Teacher> getRelease(Collection<Teacher> list, Predicate<Teacher> predicate){
        return list.stream().filter(predicate).findFirst();
    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int compareTo(Teacher other) {
    	if(other==null){
    	    return -1;
        }
        return this.id-other.getId();
    }
}
