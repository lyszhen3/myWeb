package springannotation.aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lys on 2018/11/8.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AopConfig.class)
public class Client {

	@Autowired
//	@Qualifier("secondAopTestServiceImpl")
	private AopTestService aopTestService;

	@Test
	public void test(){
//		int hello = aopTestService.hello(1);
		aopTestService.hello("1");
	}


}
