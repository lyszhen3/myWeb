package com.dynamic;

import java.lang.invoke.*;

/**
 * Created by lys on 2018/11/26.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class InvokeDynamicTest {

	public static void main(String[] args) throws Throwable {
		INDY_BootrapMethod().invokeExact("wode tian ");
	}


	public static void testMethod(String s) {
		System.out.println("hello string:" + s);
	}

	public static CallSite BootrapMethod(MethodHandles.Lookup lookup, String name, MethodType mt) throws NoSuchMethodException, IllegalAccessException {
		return new ConstantCallSite(lookup.findStatic(InvokeDynamicTest.class, name, mt));
	}

	private static MethodType MT_BootrapMethod() {
		return MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", null);
	}

	public static MethodHandle MH_BootrapMethod() throws NoSuchMethodException, IllegalAccessException {
		return MethodHandles.lookup().findStatic(InvokeDynamicTest.class,"BootrapMethod",MT_BootrapMethod());
	}

	private static MethodHandle INDY_BootrapMethod() throws Throwable {
		CallSite cs  =(CallSite)MH_BootrapMethod().invokeWithArguments(MethodHandles.lookup(),"testMethod",MethodType.fromMethodDescriptorString("(Ljava/lang/String;)V",null));
		return cs.dynamicInvoker();
	}

}
