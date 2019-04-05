import java.util.*;

/**
 * Created by lys on 2019/3/1.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class RecursiveTest {

	static Deque<Integer> list = new ArrayDeque<Integer>() {

		private static final long serialVersionUID = 4058707604119129722L;

		{
			add(1);
			add(2);
			add(3);
			add(4);
			add(7);
			add(9);
		}
	};

	public static int re( Deque<Integer> deque) {

		if (deque.size() == 0) {
			return 0;
		} else {
			deque.pop();
			return 	re(deque)+1;
		}
	}

	public static void main(String[] args) {
		int re = re(list);
		System.out.println(re);
	}
}
