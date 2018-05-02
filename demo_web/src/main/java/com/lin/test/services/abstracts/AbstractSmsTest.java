package com.lin.test.services.abstracts;

import com.lin.data.mappers.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lys on 4/26/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public abstract class AbstractSmsTest {
	@Autowired
	private TestMapper testMapper;
	protected String name;

	public abstract void setName(String name);
	public void setNamef(String name){
		this.name = name;
	}

	public void ssout(){
		int i = testMapper.testCount();
		System.out.println(i);
	}

	public String getName() {
		return name;
	}
}
