package NoClassDefFoundErrorTest;

import java.util.logging.Logger;

/**
 * Created by lys on 4/20/2019.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class NoClassDefFoundErrorTest {

	public static int i;
	public static void main(String[] args)  {
		a();
	}
	public static void a () {
		Logger wo = Logger.getLogger("wo");
		try {
			a();
		}catch (Throwable e){
//			System.out.println(i++);
			Logger.getLogger("wo").info("aa");
			B.print();
		}
	}

	public static class B{
		public static void print(){
			System.out.println("b");
		}
	}
}
