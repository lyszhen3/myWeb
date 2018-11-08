package springannotation.construction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lys on 2018/11/7.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Configuration
@ComponentScan("springannotation.construction")
public class ConfigA {

	@Bean
	public Bar1 bar1(){
		return new Bar1("hello");
	}
}
