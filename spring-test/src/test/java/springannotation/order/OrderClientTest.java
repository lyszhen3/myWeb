package springannotation.order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * Created by lys on 2019/4/10.
 * order 除了在aop中有用还有在集合中排序
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {OrderConfig.class})
public class OrderClientTest {

	@Autowired
	List<SuperOrder> list;

	@Test
	public void test() {
		list.forEach(System.out::print);
	}
}
