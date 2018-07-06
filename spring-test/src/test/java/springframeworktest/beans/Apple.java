package springframeworktest.beans;

import org.springframework.beans.factory.InitializingBean;

import java.util.Date;

/**
 * Created by lys on 7/3/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 * @see InitializingBean#afterPropertiesSet() 实现这个方法后可以在实体类实例化后执行一个init-method
 */
public class Apple implements InitializingBean {
	public String name;
	private String id;
	private Date birthday;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		name = "初始化方法";
		System.out.println("初始化后执行的方法");
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
