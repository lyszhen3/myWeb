package springframeworktest.beans.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionVisitor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.StringValueResolver;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lys on 7/8/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class ObscentityRemovingBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	private Set<String> obscenties;

	public ObscentityRemovingBeanFactoryPostProcessor(){
		this.obscenties = new HashSet<>();
	}
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		String[] beanNames = beanFactory.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			BeanDefinition bd = beanFactory.getBeanDefinition(beanName);
			StringValueResolver valueResolver = new StringValueResolver() {
				@Override
				public String resolveStringValue(String strVal) {
					if(isObscene(strVal)){
						return "*******";
					}
					return strVal;
				}
			};
			BeanDefinitionVisitor visitor = new BeanDefinitionVisitor(valueResolver);
			visitor.visitBeanDefinition(bd);
		}
	}

	@SuppressWarnings("WeakerAccess")
	public boolean isObscene(Object value){
		String potentialObscentity = value.toString().toUpperCase();
		return this.obscenties.contains(potentialObscentity);
	}

	public Set<String> getObscenties() {
		return obscenties;
	}

	public void setObscenties(Set<String> obscenties) {
		this.obscenties.clear();
		for (String obscenty : obscenties) {
			this.obscenties.add(obscenty.toUpperCase());
		}
	}
}
