package springframeworktest.beans.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by lys on 7/9/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class TestEvent extends ApplicationEvent {

	public String msg;


	/**
	 * Create a new ApplicationEvent.
	 *
	 * @param source the object on which the event initially occurred (never {@code null})
	 */
	public TestEvent(Object source) {
		super(source);
	}


	public TestEvent(Object source,String msg){
		super(source);
		this.msg = msg;
	}

	public void print(){
		System.out.println(msg);
	}
}
