package example.designpattern.structural.decorator;

/**
 * Created by lys on 5/4/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class BlackBorderDecorator extends ComponentDecorator {


	public BlackBorderDecorator(Component component) {
		super(component);
	}

	@Override
	public void display() {
		setBlackBorder();
		super.display();
	}

	private void setBlackBorder() {
		System.out.println("为构建增加黑边框");
	}
}
