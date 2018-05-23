package example.designpattern.structural.adapter.defaultadapter;

import java.util.ArrayList;

/**
 * Created by lys on 5/2/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class ConcreteServiceClass extends AbstractServiceClass{

	@Override
	public void saylive() {
		System.out.println("嗯？");
	}

	public static void main(String[] args) {
		ArrayList list= new ArrayList();
	}

}
