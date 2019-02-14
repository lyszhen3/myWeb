package com.java8Features;


import com.google.common.base.Supplier;

import java.util.function.Consumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * Created by lys on 2019/2/14.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class ConsumerTests {

	public static void main(String[] args) {
		Consumer<String> c = str->{
			String ii = "dalin"+str;
			System.out.println(ii);
		};

		c.accept("say what");

		Predicate<String> ni = (String str) -> str.equals("ni");
		boolean test = ni.and(str -> str.contains("i")).test("ni");
		System.out.println(test);

		IntPredicate predicate = (int i) -> i == 188;
		System.out.println(predicate.test(188));
		Predicate<Integer> integerPredicate = (Integer i) -> i == 288;
		System.out.println(integerPredicate.test(3));
	}
}
