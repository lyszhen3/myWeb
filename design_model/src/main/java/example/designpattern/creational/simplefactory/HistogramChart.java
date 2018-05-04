package example.designpattern.creational.simplefactory;

/**
 * Created by lys on 2/24/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class HistogramChart implements Chart{
    @Override
    public void display() {
        System.out.println("显示柱形图");
    }
     public HistogramChart() {
        System.out.println("创建柱状图！");
    }

}
