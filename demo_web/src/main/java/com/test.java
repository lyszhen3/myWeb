package com;

import java.io.UnsupportedEncodingException;

/**
 * Created by pc on 2017-01-11.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class test {
	public static void main(String[] args) throws UnsupportedEncodingException {
		Thread thread = new Thread(() -> {
			System.out.println("wo");
		});
		thread.start();
	}

	public void test1() {
		System.out.println("test_1_1");
	}

	public void masterTest() {
		System.out.println("master");
	}

	public void test2() {
		System.out.println("test_2");
		System.out.println();

	}
}
