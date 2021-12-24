package leetcode.algorithm;

import java.util.Random;

/**
 * @author LinYuanSheng
 * @date 2021/12/22
 * 链表排序
 * lists=[[1,2,3],[1,3,4],[3,4,5]]
 * <p>
 * 输出
 * 1-1-2-3-3-3-4-4-5
 */
public class LinkedListSort {

	static class Node {
		public Node(int value) {
			this.value = value;
		}

		private int value;

		private Node next;

		private int getLength() {
			int length = 1;
			Node current = this;
			while (current.next != null) {
				length++;
				current = current.next;
			}
			return length;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public Node getNext() {
			return next;
		}

		public void setNext(Node next) {
			this.next = next;
		}

		public void addNode(Node node) {
			if (this.next == null) {
				this.next = node;
				return;
			}
			this.next.addNode(node);

		}

		public Node indexOf(int index) {
			if (index == 0) {
				return this;
			}
			Node r = this;
			while (index > 0) {
				r = r.next;
				if (r == null) {
					return null;
				}
				index--;
			}
			return r;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder(String.valueOf(value));
			if (this.next != null) {
				builder.append("->").append(this.next);
			}
			return builder.toString();
		}
	}

	public static Node combine(Node[] nodes) {

		//计算最大链表长度
		int maxL = 0;

		for (Node node : nodes) {
			if (maxL < node.getLength()) {
				maxL = node.getLength();
			}
		}

		//最终输出的链表
		Node fNode = null;

		//存放指针
		Node[] nodeIndex = new Node[nodes.length];
		for (int i = 0; i < maxL; i++) {
			for (int j = 0; j < nodes.length; j++) {
				final Node node = nodes[j];

				final Node ele = node.indexOf(i);

				if (ele == null) {
					continue;
				}

				if (fNode == null) {
					//如果没有则new一个
					fNode = new Node(ele.getValue());
					//将第j个链表的node放入指针中
					nodeIndex[j] = fNode;
					continue;
				}
				//获取指针，
				final Node indexNode = nodeIndex[j];
				final Node insertNode = new Node(ele.getValue());
				if (indexNode == null) {
					//如果指针不存在
					insertNode(fNode, insertNode);
				} else {
					insertNode(indexNode, insertNode);
				}
				//更新指针
				nodeIndex[j] = insertNode;

			}
		}

		return fNode;
	}

	private static void insertNode(Node fNode, Node insertNode) {
		if (insertNode == null || fNode == null) {
			return;
		}
		Node current = fNode;
		while (insertNode.getValue() >= current.getValue()) {
			//当插入的元素大于当前元素时
			final Node next = current.getNext();
			if (next == null) {
				current.next = insertNode;
				return;
			}

			if (insertNode.getValue() < next.getValue()) {
				//如果小于下一节点的值，则插入
				insertNode.next = next;
				current.next = insertNode;
				return;
			}
			current = current.getNext();

		}
		//如果小于被插入的链表，则插入最前面
		insertNode.next = fNode;

	}

	public static void main(String[] args) {
		final Random random1 = new Random();
		final int it = random1.nextInt(10);
		System.out.println(it);
		Node[] nodes = new Node[3];
		Node node1 = null;
		Node node2 = null;
		Node node3 = null;
		for (int i = 0; i < 3; i++) {

			if (node1 == null) {
				node1 = new Node(2 * (i + 1));
			} else {
				node1.addNode(new Node(2 * (i + 1)));
			}

			if (node2 == null) {
				node2 = new Node(3 * (i + 1));
			} else {
				node2.addNode(new Node(3 * (i + 1)));
			}

			if (node3 == null) {
				node3 = new Node(4 * (i + 1));
			} else {
				node3.addNode(new Node(4 * (i + 1)));
			}

		}
		nodes[0] = node1;
		nodes[1] = node2;
		nodes[2] = node3;
		for (Node node : nodes) {
			System.out.println(node);
		}
		final Node combine = combine(nodes);
		System.out.println(combine);

	}
}
