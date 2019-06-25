package springframeworktest.beans;

import org.junit.Test;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import springframeworktest.beans.aware.AwareTest;
import springframeworktest.beans.event.TestEvent;

/**
 * Created by lys on 7/3/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class ClientTests {

	private static final ApplicationContext xmlContext =
			new ClassPathXmlApplicationContext("beanFactory.xml");

	@Test
	public void testXmlBean() {
		Apple bean = xmlContext.getBean(Apple.class);
		System.out.println(bean.getName());
	}
	@Test
	public void testAware(){
		AwareTest awareTest = xmlContext.getBean(AwareTest.class);
		awareTest.testAware();
	}

	/**
	 *方法1
	 * @see springframeworktest.beans.propertyeditor.DatePropertyEditor#setAsText(String)
	 * 方法2
	 * @see springframeworktest.beans.propertyeditor.DatePropertyEditorRegistar#registerCustomEditors(PropertyEditorRegistry)
	 * and beanFactory.xml
	 */
	@Test
	public void testEditor(){
		Apple bean = xmlContext.getBean(Apple.class);
		System.out.println(bean.getBirthday());
	}

	@Test
	public void testReplace(){
		ReplaceMethodClass bean = xmlContext.getBean(ReplaceMethodClass.class);

	}

	@Test
	public void testPostProcessor(){
		ConfigurableListableBeanFactory bf = new XmlBeanFactory(new ClassPathResource("beanFactory.xml"));
		BeanFactoryPostProcessor bfbb = (BeanFactoryPostProcessor)bf.getBean("bfpp");
		bfbb.postProcessBeanFactory(bf);
		System.out.println(bf.getBean("simpleBean"));
	}

	@Test
	public void testEvent(){
		TestEvent event = new TestEvent("hello","msg");
		xmlContext.publishEvent(event);
	}
}
