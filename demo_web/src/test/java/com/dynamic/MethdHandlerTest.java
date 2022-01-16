package com.dynamic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lys on 2018/11/26.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class MethdHandlerTest {
	static class ClassA {
		public void println(String arg) {
			System.out.println("A:"+arg );
		}
	}

	public static void main(String[] args) throws Throwable {
		Object obj = System.currentTimeMillis() % 2 == 0 ? System.out : new ClassA();
//		getPrintMH(obj).invokeExact("wocao");
		getPrint(obj,"wocao");
	}

	private static MethodHandle getPrintMH(Object receiver) throws NoSuchMethodException, IllegalAccessException {
		MethodType mt = MethodType.methodType(void.class, new Class[]{String.class});
		return MethodHandles.lookup().findVirtual(receiver.getClass(), "println", mt).bindTo(receiver);
	}

	public static void getPrint(Object obj,String arg) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Class<?> aClass = obj.getClass();
		Method println = aClass.getMethod("println", String.class);
		println.invoke(obj,arg);
	}
}
