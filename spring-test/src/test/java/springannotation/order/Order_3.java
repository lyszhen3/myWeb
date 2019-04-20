package springannotation.order;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by lys on 2019/4/10.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Component
@Order(3)
public class Order_3 implements SuperOrder {
	public Order_3() {
		System.out.println("order3 instance");
	}
}
