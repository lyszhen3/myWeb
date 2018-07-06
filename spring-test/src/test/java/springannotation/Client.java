package springannotation;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springannotation.component.ConfigA;
import springannotation.configuration.ConfigB;

/**
 * Created by lys on 7/5/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Client {
	/**
	 * @see ConfigA  使用了{@link org.springframework.stereotype.Component} 来注入spring容器中，bar被创建了两次
	 */
	@Test
	public void testComponent() {
		ApplicationContext context = new AnnotationConfigApplicationContext(ConfigA.class);
		Bar bar = context.getBean(Bar.class);
		System.out.println(bar);
		Foo foo = context.getBean(Foo.class);
		System.out.println(foo.getBar());
	}

	/**
	 * @see ConfigB 使用了{@link org.springframework.context.annotation.Configuration} 来注入spring 容器中bar 只被创建一次
	 */
	@Test
	public void testConfiguration(){
		ApplicationContext context = new AnnotationConfigApplicationContext(ConfigB.class);
		Bar bar = context.getBean(Bar.class);
		System.out.println(bar);
		Foo foo = context.getBean(Foo.class);
		System.out.println(foo.getBar());
	}
}
