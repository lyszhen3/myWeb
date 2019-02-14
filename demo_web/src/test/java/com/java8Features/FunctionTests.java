package com.java8Features;

import com.sun.tools.javac.Main;

import java.util.function.Function;

/**
 * Created by lys on 2019/2/13.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class FunctionTests {

	@FunctionalInterface
	interface StringMapper{
		int map(String str);
	}



	public static void main(String[] args) throws Exception {
		StringMapper mapper = String::length;
		System.out.println(mapper.map("chen"));
		Function<Integer, Integer> squarel = (Integer x) -> x*x;
		System.out.println(squarel.apply(5));

		System.out.println(validate("我的错咯",str->str.length()>3?"length>3":str));
		//运行之前执行
		System.out.println(compose("银行账目",str->str+"->统计中"));
		//运行之后执行
		System.out.println(andThen("银行账目",str-> str+"->统计中"));


//		Main.main(new String[]{"demo_web/src/test/java/com/java8Features/FunctionTests.java"});
	}

	public static String validate(String str, Function<String, String> function){
		return function.apply(str);
	}


	public static String compose(String str, Function<String, String> function){
		 return function.compose(zrr-> zrr+"->统计前").apply(str);
	}
	public static String andThen(String str, Function<String, String> function){
		return function.andThen(zzr->zzr+"->统计后").apply(str);
	}
}
