package springannotation.dependon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lys on 2022/11/5.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */

@Component
public class NormalBean {
	@Autowired
	public void setNormalBean_2(NormalBean_2 normalBean_2) {
		this.normalBean_2 = normalBean_2;
	}
	private NormalBean_2 normalBean_2;



}
