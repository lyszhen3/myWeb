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
	private BarFactoryBean barFactoryBean;


	/**
	 * 由于factory bean的作用 被设置了属性
	 */
//	@Autowired
	private Bar bar;

	@Test
	public void test() {
		Bar object = null;
		try {
			object = barFactoryBean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(barFactoryBean.getClass());
		Bar bean = (Bar) barFactoryBean.beanFactory.getBean("bar");
		Object factoryBeanTest = this.barFactoryBean.beanFactory.getBean("barFactoryBean");
		System.out.println("bar1::" + bean);
		System.out.println(this.barFactoryBean.getObjectType());

		//barFactoryBean 和 barFactoryBean.getObject() 生产出得是同一个对象
		System.out.println("bar2::" + object);
		System.out.println("bar3::"+factoryBeanTest);
//		System.out.println(bar.getName());

	}


	@Test
	public void test_main() {

		ApplicationContext context = new AnnotationConfigApplicationContext(FactoryBeanConfig.class);
		Object barFactoryBean = context.getBean("barFactoryBean");
		Object $barFactoryBean = context.getBean("&barFactoryBean");
		Object bar = context.getBean("bar");
		System.out.println(((Bar) barFactoryBean).getName());
		System.out.println(((BarFactoryBean) $barFactoryBean).getObjectType());
		//bar bean
		System.out.println("bar bean :" + ((Bar)bar).getName());
		//factoryBean 生产得bar bean
		System.out.println("factoryBean bar:" + barFactoryBean);

		//factoryBean
		System.out.println("factoryBean:" + $barFactoryBean);
	}

}
