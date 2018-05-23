package example.designpattern.structural.decorator;

/**
 * Created by lys on 5/4/2018.
 * 文本框类
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class TextBox extends Component {
	@Override
	public void display() {
		System.out.println("显示文本框");
	}
}
