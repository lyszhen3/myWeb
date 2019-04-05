package synaxuncrateenchase;

/**
 * Created by lys on 2019/4/4.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class TestCrate {
	public static void main(String[] args) {
		Integer a = 1;
		Integer b = 2;
		Integer c = 3;
		Integer d = 3;
		Integer e = 321;
		Integer f = 321;
		Long g = 3L;
		Double h = 10.0;
		Double i = 10.0;
		System.out.println(c == d);
		System.out.println(e == f);
		System.out.println(c == (a + b));
		System.out.println(c.equals((a+b)));
		System.out.println(g == (a+b));
		System.out.println(g.equals(a+b));
		System.out.println(g == 3);
		System.out.println(h == i);
		System.gc();
	}
}
