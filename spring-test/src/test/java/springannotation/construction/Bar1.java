package springannotation.construction;

import org.springframework.stereotype.Component;

/**
 * Created by lys on 2018/11/7.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Component
public class Bar1 {

	public Bar1(String name){
		System.out.println(name);
	}
}
