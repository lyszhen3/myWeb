package springannotation.dependon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author LinYuanSheng
 * @date 2022/11/17
 */
//@Component
public class CycleBean {
	private CycleBean_2 cycleBean_2;

	@Autowired
	public CycleBean(CycleBean_2 cycleBean_2) {
		this.cycleBean_2 = cycleBean_2;
	}
}
