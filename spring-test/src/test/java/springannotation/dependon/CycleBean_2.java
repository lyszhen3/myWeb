package springannotation.dependon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author LinYuanSheng
 * @date 2022/11/17
 */
//@Component
public class CycleBean_2 {

	private CycleBean cycleBean;

	@Autowired
	public CycleBean_2(CycleBean cycleBean) {
		this.cycleBean = cycleBean;
	}
}
