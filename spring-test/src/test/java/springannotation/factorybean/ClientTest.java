package springannotation.factorybean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springannotation.Bar;


/**
 * Created by lys on 2019/1/23.
 *
 * &factoryBeanTest 代表获取工厂类, factoryBeanTest 代表获取该工产类里维持的对象
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FactoryBeanConfig.class)
public class ClientTest {

	@Autowired
	private FactoryBeanTest factoryBeanTest;


	/**
	 * 由于factory bean的作用 被设置了属性
	 */
//	@Autowired
	private Bar bar;

	@Test
	public void test() {
		Bar object = null;
		try {
			object = factoryBeanTest.getObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(factoryBeanTest.getClass());
		Bar bean = (Bar) factoryBeanTest.beanFactory.getBean("bar");
		Object factoryBeanTest = this.factoryBeanTest.beanFactory.getBean("factoryBeanTest");
		System.out.println("bar1::" + bean);
		System.out.println(this.factoryBeanTest.getObjectType());
		System.out.println("bar2::" + object);
		System.out.println("bar3::"+factoryBeanTest);
//		System.out.println(bar.getName());

	}

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(FactoryBeanConfig.class);
		Object factoryBeanTest = context.getBean("factoryBeanTest");
		Object $factoryBeanTest = context.getBean("&factoryBeanTest");
		Object bar = context.getBean("bar");
		System.out.println(((Bar) factoryBeanTest).getName());
		System.out.println(((FactoryBeanTest) $factoryBeanTest).getObjectType());
		System.out.println(bar);
		System.out.println(factoryBeanTest);
		System.out.println($factoryBeanTest);
	}

}
