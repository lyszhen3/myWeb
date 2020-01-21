package springannotation.construction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Created by lys on 2018/11/7.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Configuration
@ComponentScan("springannotation.construction")
@PropertySources(@PropertySource("classpath:spring.properties"))
public class ConfigA {

    @Bean
    public Foo1 foo1(Foo2 foo2){
        Foo1 foo1 = new Foo1();
        foo1.setFoo2(foo2);
        return foo1;
    }

    @Bean
    public Foo2 foo2(){
        return new Foo2();
    }
}
