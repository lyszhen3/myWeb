package springannotation.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springannotation.Bar;
import springannotation.Foo;

/**
 * Created by lys on 7/5/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Configuration
public class ConfigB {

	@Bean
	public Bar bar(){
		return new Bar();
	}
	@Bean
	public Foo foo(){
		return new Foo(bar());
	}
}
