package springannotation.factorybean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springannotation.Bar;

/**
 * Created by lys on 2019/1/23.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Configuration
@ComponentScan("springannotation.factorybean")
 class FactoryBeanConfig {

	@Bean
	public Bar bar(){
		return new Bar();
	}
}
