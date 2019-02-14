package initializetest;

/**
 * Created by lys on 2019/2/14.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class InitializeBar extends InitializeFoo{
	public static String str;
	static {
		System.out.println("bar 初始化");
		str = "11";
	}

}

/**
 * 如此一来bar就不会初始化
 */
class zz{
	 public static void main(String[] args) {
		 System.out.println(InitializeBar.i);
	 }
 }
