package springannotation.factorybean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;
import springannotation.Bar;

/**
 * Created by lys on 2019/1/23.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Component
class BarFactoryBean implements FactoryBean<Bar>, BeanFactoryAware {

	BeanFactory beanFactory;

	private volatile Bar singleton;

	@Override
	public Bar getObject() throws Exception {

		// "factoryBeanTest" 获取bar 和 getObject() 为同一个
//		Bar bar =(Bar) beanFactory.getBean("bar");
		//工厂 bean自己维护了一个单例 所以和 "bar" 不是同一个
		Bar bar = getSingleton();
		bar.setName("天才");
		return bar;
	}

	/**
	 * 创建个单例
	 *
	 * @return
	 */
	private Bar getSingleton() {
		if (singleton == null) {
			synchronized (this) {
				if (singleton == null) {
					singleton = new Bar();
				}
			}
		}
		return singleton;
	}

	@Override
	public Class<?> getObjectType() {
//	  return beanFactory.getBean("bar").getClass();
		return Bar.class;
	}


	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
}
