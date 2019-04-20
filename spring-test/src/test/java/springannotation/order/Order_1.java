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
@Order(1)
@Component
public class Order_1 implements SuperOrder {
	public Order_1() {
		System.out.println("order1 instance");
	}
}
