package springannotation.dependon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lys on 2022/11/5.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DependOnConfig.class)
public class DependOnTest {


	@Test
	public void testDepend() {
		final AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(DependOnConfig.class);

		final Object normalBean = annotationConfigApplicationContext.getBean("normalBean");
		System.out.println(normalBean);

	}

	@Test
	public void testCycleDepend() {
		//循环构造依赖会报错
		final AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(DependOnConfig.class);

		final Object normalBean = annotationConfigApplicationContext.getBean("normalBean");
		System.out.println(normalBean);
	}
}
