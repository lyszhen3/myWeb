package algorithm;

import java.util.HashMap;

public class LRUCache {
	//数组的容量
	public int capacity;
	//数组
	public Node[] array;
	//双向循环链接的头结点(哨兵节点)
	public Node head;
	//存储的元素个数
	public int num = 0;

	public LRUCache(int capacity) {
		this.capacity = capacity;
		array = new Node[capacity];
		head = new Node(-1, -1);
	}

	public int get(int key) {
		//根据key得到对应节点
		Node node = getNode(key);
		if (null == node) {
			return -1;
		}
		//将此节点移动到双向循环链表的末尾
		Node prefix = node.prefix;
		Node next = node.next;
		//先从双向循环链表中去掉自己 然后再拼到链接的末尾
		prefix.next = next;
		next.prefix = prefix;
		Node p1 = head.prefix;
		p1.next = node;
		node.prefix = p1;
		node.next = head;
		head.prefix = node;
		return node.getVal();

	}

	private Node getNode(int key) {
		//对key取capacity模然后得到数组下标
		int hKey = key % capacity;
		//拿到头结点
		Node node = array[hKey];
		if (null == node) {
			return null;
		}
		//按链表往后查找
		while (null != node && node.key != key) {
			node = node.hNext;
		}
		return node;
	}

	public void put(int key, int value) {
		//根据key得到对应节点
		Node node = getNode(key);
		if (null == node) {
			//元素个数加加
			num++;
			//如果超过容量 则去掉链表头一个非哨兵节点
			if (num > capacity) {
				Node next = head.next;
				if (null == next) {
					return;
				}
				int dKey = next.getKey();
				//根据待删除节点的key求得节点所在的数组下标
				int hdKey = dKey % capacity;
				Node tmp = array[hdKey];
				Node tPrefix = null;
				while (null != tmp && tmp.getKey() != dKey) {
					tPrefix = tmp;
					tmp = tmp.hNext;
				}
				//删除数组槽位的链表中的此节点
				if (null != tPrefix) {
					tPrefix.hNext = next.hNext;
				} else {
					array[hdKey] = next.hNext;
				}
				//删除循环双向链表中的此节点
				node = head;
				while (null != node.next && node.next.getKey() != dKey) {
					node = node.next;
				}
				node.next = next.next;
				next.next.prefix = node;
				num--;
			}
			//定义新节点 将节点插入到数组槽位中的链表 同时也插入双向循环链表
			Node nNode = new Node(key, value);
			int hKey = key % capacity;
			Node prefix = array[hKey];
			if (null == prefix) {
				array[hKey] = nNode;
			} else {
				while (null != prefix.hNext) {
					prefix = prefix.hNext;
				}
				prefix.hNext = nNode;
			}
			prefix = head.prefix;
			if (null == prefix) {
				head.prefix = nNode;
				head.next = nNode;
				nNode.prefix = head;
				nNode.next = head;
			} else {
				prefix.next = nNode;
				nNode.prefix = prefix;
				nNode.next = head;
				head.prefix = nNode;
			}
		} else {
			//有此节点 则更新value 且将此节点移动到链表末尾
			node.setVal(value);
			node.prefix.next = node.next;
			node.next.prefix = node.prefix;
			Node prefix = head.prefix;
			prefix.next = node;
			node.prefix = prefix;
			node.next = head;
			head.prefix = node;
		}
	}

	//节点对象
	class Node {
		int key;
		int val;
		//双向循环链表的上个节点
		Node prefix;
		//双向循环链表的下个节点
		Node next;
		//数组槽位的链表的下个节点
		Node hNext;

		public Node(int key, int val) {
			this.key = key;
			this.val = val;
		}

		public int getKey() {
			return key;
		}

		public int getVal() {
			return val;
		}

		public void setVal(int val) {
			this.val = val;
		}
	}

	/**
	 * hash map 实现
	 */
	class HashMapLRUCache<K,V>{

		private final int MAX_CAPACITY;

		private HashMap<K, Node<K,V>> hashMap;

		private Node<K,V> first;

		private Node<K,V> last;

		public HashMapLRUCache(int capacity) {
			MAX_CAPACITY = capacity;
			hashMap = new HashMap<>();
		}

		public V get(K key){
			final Node<K, V> kvNode = hashMap.get(key);
			if (kvNode == null) {
				return null;
			}
			move2First(kvNode);
			return kvNode.value;
		}

		public void put(K k, V v) {
			Node<K, V> kvNode = hashMap.get(k);

			if (kvNode == null) {
				if (hashMap.size() >= MAX_CAPACITY) {
					hashMap.remove(last.key);
					removeLast();
				}
				kvNode = new Node<>();
				kvNode.key = k;
			}
			kvNode.value = v;
			move2First(kvNode);
			hashMap.put(k, kvNode);

		}

		public void remove(K v) {

		}

		private void removeLast() {
			if (last == null) {
				return;
			}

			last = last.pre;
			if (last == null) {
				//last和first 是同一个
				first = null;
			} else {
				last.next = null;
			}
		}

		/**
		 * 移到顶部, 底部的会被淘汰
		 * @param kvNode
		 */
		private void move2First(Node<K, V> kvNode) {
			if (kvNode == null) {
				return;
			}
			if (first == kvNode) {
				return;
			}

			if (kvNode.pre != null) {
				kvNode.pre.next = kvNode.next;
			}
			if (first == null || last == null) {
				first = last = kvNode;
			}

			if (kvNode.next != null) {
				kvNode.next.pre = kvNode.pre;
			}
			first.pre = kvNode;
			kvNode.next = first;
			first = kvNode;
			kvNode.pre = null;
		}

		class Node<K,V>{
			K key;
			V value;

			Node<K, V> pre;

			Node<K,V> next;
		}
	}
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
