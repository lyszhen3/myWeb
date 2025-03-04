package springannotation.dependon;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created by lys on 2022/11/5.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@ExtendWith(SpringExtension.class)
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
