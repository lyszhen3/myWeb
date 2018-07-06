package springframeworktest.beans.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import springframeworktest.beans.Apple;

/**
 * Created by lys on 7/5/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class AwareTest implements BeanFactoryAware {

	private BeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public void testAware() {
		Apple bean = beanFactory.getBean(Apple.class);
		System.out.println(bean.getName()+":"+bean.getBirthday());
	}

}
