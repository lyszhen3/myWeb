package springannotation;

import org.springframework.core.annotation.Order;

/**
 * Created by lys on 7/5/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Bar {

	public Bar(){
		System.out.println("bar::实例化");
	}

	private  String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
