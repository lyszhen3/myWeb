import abstractTest.Son;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by pc on 2017-10-09.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class ListToMap {
    public static void main(String[] args) {
        String[] ids = {"我的"};
        List<Son> list = new ArrayList<>();
        Son son = new Son();
        son.setId(1L);
        son.setName("我的");
        Son son2 = new Son();
        son2.setName("你的");
        son2.setId(2L);
        Son son3 = new Son();
        son3.setId(1L);
        son3.setName("他的");
        list.add(son);
        list.add(son2);
        list.add(son3);

        Map<Long, Son> collect = list.stream().collect(Collectors.toMap(Son::getId, Function.identity(),(key1, key2)->key2));
        list.stream().filter(rr-> !Arrays.asList(ids).contains(rr.getName())).collect(Collectors.toList()).forEach(rrr-> System.out.println(rrr.getName()));
//        System.out.println(collect);

        listGroupToMap();
    }

    public static void listGroupToMap(){
        List<Student> list = new ArrayList<>();
        list.add(new Student(2L,"浙江大学"));
        list.add(new Student(4L,"浙江大学"));
        list.add(new Student(3L,"清华大学"));

        list.add(new Student(3L,"清华大学"));

        Map<String, Double> scoreMembers = list.stream().collect(Collectors.toMap(Student::getSchool, s -> s.getPoint().doubleValue(), Double::sum, Hashtable::new));

        System.out.println(scoreMembers);

    }
    static class Student{
        private Long point;
        private String school;
        public Student(Long point, String school){
            this.point = point;
            this.school = school;
        }
        public Long getPoint() {
            return point;
        }

        public void setPoint(Long point) {
            this.point = point;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }
    }

}
