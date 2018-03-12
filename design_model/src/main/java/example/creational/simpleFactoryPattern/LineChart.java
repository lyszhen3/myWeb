package example.creational.simpleFactoryPattern;

/**
 * Created by lys on 2/24/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class LineChart implements Chart{
    @Override
    public void display() {
        System.out.println("显示折线图！");
    }
     public LineChart() {
        System.out.println("创建折线图！");
    }
}
