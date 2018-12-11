import org.junit.Test;

import java.util.*;

/**
 * Created by pc on 2017-10-11.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class Testtest {

	@org.junit.Test
	public void test() {
		Testtest test = null;
		System.out.println("上一个test" + test);
		if (test == null && (test = new Testtest()) == null) {
			System.out.println("空空");
		}
		System.out.println("下一个test" + test);
	}

	static <R> R insert(R r, Integer i) {
		return r;
	}


	public static void swap(int a, int b) {
		System.out.println("交换前:" + a);
		System.out.println("交换前:" + b);
		a ^= b;
		b ^= a;
		a ^= b;
//		a^=b^=a^=b;
		System.out.println("交换后:" + a);
		System.out.println("交换后" + b);
	}


	@Test
	public void testBean() {

		HashMap<String, Integer> map = new HashMap<>(10);
		map.put("1", 1);
		map.put("2", 2);
		map.put("3", 3);
		map.put("4", 4);
		OptionalInt reduce = map.entrySet().stream().mapToInt(Map.Entry::getValue).reduce((a, b) -> a + b);
		System.out.println(reduce.getAsInt());

	}

	/**
	 * cpu处理器个数
	 */
	@Test
	public void executorPool() {
		int i = Runtime.getRuntime().availableProcessors();
		System.out.println(i);
	}

	@Test
	public void eeee() {
		Optional<Object> empty = Optional.of(11);

		System.out.println(empty.orElse(null));
	}

	public int[] testNum() {
		int[] nums = {0, 1, 2, 4};
		int target = 5;
		Map<Integer, Integer> map = new HashMap<>(nums.length);
		for (int i = 0; i < nums.length; i++) {
			int w = target - nums[i];
			if (map.containsKey(w)) {
				return new int[]{map.get(w), i};
			}
			map.put(nums[i], i);
		}

		throw new IllegalArgumentException("没找到哦");
	}

	//2->4->3, 5->6->4
	public static ListNode tt() {
		ListNode l1 = new ListNode(1),
				ls2 = new ListNode(4),
				ls3 = new ListNode(3);
		l1.next = ls2;
		ls2.next = ls3;
		ListNode l2 = new ListNode(9),
				l2s2 = new ListNode(2),
				l2s3 = new ListNode(1);
//				l2s4 = new ListNode(9),
//				l2s5 = new ListNode(9),
//				l2s6 = new ListNode(9),
//				l2s7 = new ListNode(9),
//				l2s8 = new ListNode(9),
//				l2s9 =new ListNode(9),
//				l2s10 =new ListNode(8);
		l2.next = l2s2;
		l2s2.next = l2s3;
//		l2s3.next = l2s4;
//		l2s4.next = l2s5;
//		l2s5.next = l2s6;
//		l2s6.next = l2s7;
//		l2s7.next = l2s8;
//		l2s8.next = l2s9;
//		l2s9.next = l2s10;


		ListNode index1 = l1;
		ListNode index2 = l2;
		ListNode listNode=new ListNode(0);
		ListNode index = listNode;
		while(index1!=null || index2!=null){


			int i = index1!=null? index1.val:0;
			int j = index2!=null? index2.val:0;
			int sum = i+j;
			if((sum+index.val)/10>0){
				index.next = new ListNode((sum+index.val)/10);
				index.val = (sum+index.val)%10;
			}else{
				index.val = sum+index.val;
			}
			index1 = index1!=null?index1.next:null;
			index2 = index2!=null?index2.next:null;
			if((index1!=null||index2!=null)&&index.next==null ){
				index.next = new ListNode(0);

			}
			index = index.next;

		}

		ListNode out = listNode;
		while (out!=null){
			System.out.print(out.val);
			out = out.next;
		}

		return listNode;
	}

	static class ListNode {
		int val;
		ListNode next;

		ListNode(int i) {
			this.val = i;
		}

	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("wo");
		list.add("wo");
		list.add(1,"ta");
		list.forEach(System.out::println);
//		swap(7, 4);
//		tt();
	}
}


