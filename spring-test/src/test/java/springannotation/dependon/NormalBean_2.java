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
public class NormalBean_2 {
	private NormalBean normalBean;

	@Autowired
	public void setNormalBean(NormalBean normalBean) {
		this.normalBean = normalBean;
	}
}
