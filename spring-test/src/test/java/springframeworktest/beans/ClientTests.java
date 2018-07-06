package springframeworktest.beans;

import org.junit.Test;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import springframeworktest.beans.aware.AwareTest;

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
}
