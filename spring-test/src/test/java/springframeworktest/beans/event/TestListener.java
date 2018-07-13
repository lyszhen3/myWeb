package springframeworktest.beans.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created by lys on 7/9/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class TestListener implements ApplicationListener {
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof TestEvent){
			((TestEvent) event).print();
		}
	}
}
