package springannotation.aop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created by lys on 2018/11/8.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AopConfig.class)
public class Client {

	@Autowired
//	@Qualifier("secondAopTestServiceImpl")
	private AopTestService aopTestService;

	@Test
	public void test() {
//		int hello = aopTestService.hello(1);
		String hello = aopTestService.hello("1");
		System.out.println("response:" + hello);
	}

}
