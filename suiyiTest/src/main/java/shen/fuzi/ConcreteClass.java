package shen.fuzi;

/**
 * Created by lys on 6/27/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class ConcreteClass extends AbstarctClass{


	@Override
	public void sout() {
		System.out.println("我是小弟");
		super.diaosout();
	}

	public static void main(String[] args) {
		AbstarctClass abstarctClass = new ConcreteClass();
		abstarctClass.sout();
	}
}
