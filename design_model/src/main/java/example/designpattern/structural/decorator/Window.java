package example.designpattern.structural.decorator;

/**
 * Created by lys on 5/4/2018.
 * 窗体类：具体构件
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Window extends Component {
	@Override
	public void display() {
		System.out.println("显示窗体！");
	}
}
