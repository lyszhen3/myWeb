package springannotation.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springannotation.Bar;
import springannotation.Foo;

import javax.swing.*;
import java.util.List;

/**
 * Created by lys on 2019/4/10.
 * order 除了在aop中有用还有在集合中排序
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {OrderConfig.class})
public class OrderClientTest {

	@Autowired
	List<SuperOrder> list;
	@Test
	public void test(){
		list.forEach(System.out::print);
	}
}
