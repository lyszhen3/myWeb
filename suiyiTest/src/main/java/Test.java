import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by pc on 2017-10-11.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class Test {

	@org.junit.Test
	public void test() {
		Test test = null;
		System.out.println("上一个test" + test);
		if (test == null && (test = new Test()) == null) {
			System.out.println("空空");
		}
		System.out.println("下一个test" + test);
	}


	public static void main(String[] args) {

		ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
		queue.offer(1);
		queue.offer(2);
		queue.offer(3);
		System.out.println(queue.peek());
		System.out.println(queue.peek());

	}

	@org.junit.Test
	public void testBean() {

		HashMap<String, Integer> map = new HashMap<>(10);
		map.put("1", 1);
		map.put("2", 2);
		map.put("3", 3);
		map.put("4", 4);
		OptionalInt reduce = map.entrySet().stream().mapToInt(Map.Entry::getValue).reduce((a, b) -> a + b);
		System.out.println(reduce.getAsInt());

	}

}


