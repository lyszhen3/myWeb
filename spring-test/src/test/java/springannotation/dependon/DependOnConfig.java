package springannotation.dependon;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lys on 2022/11/5.
 * 测试依赖和循环依赖
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Configuration
@ComponentScan("springannotation.dependon")
public class DependOnConfig {

}
