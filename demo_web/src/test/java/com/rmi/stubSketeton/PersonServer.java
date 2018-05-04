package com.rmi.stubSketeton;

/**
 * Created by lys on 5/3/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class PersonServer implements Person {
	private int age;
	private String name;

	public PersonServer(String name, int age) {
		this.age = age;
		this.name = name;
	}

	@Override
	public int getAge() throws Throwable {
		return this.age;
	}

	@Override
	public String getName() throws Throwable {
		return this.name;
	}
}
