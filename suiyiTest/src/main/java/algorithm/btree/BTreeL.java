package algorithm.btree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LinYuanSheng
 * @date 2020/11/19
 * 有1<非子结点(根节点外)<=M
 * ceil(M/2)-1<=键值数<=M-1
 */
public class BTreeL<I, V> {

	private TreeNode<I, V> rootNode;

	public TreeNode<I, V> getRootNode() {
		return rootNode;
	}

	public void setRootNode(TreeNode<I, V> rootNode) {
		this.rootNode = rootNode;
	}

	public BTreeL(int M) {
		this.M = M;
	}

	private int M = 3;

	private int minNodes = M / 2;

	private int maxNodes = M - 1;

	class Node<I, V> {
		//索引
		private I index;
		//值
		private V value;
		private Node<I,V> next;

		public Node<I, V> getNext() {
			return next;
		}

		public void setNext(Node<I, V> next) {
			this.next = next;
		}

		public I getIndex() {
			return index;
		}

		public void setIndex(I index) {
			this.index = index;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}
	}

	class TreeNode<I, V> {
		/**
		 * 当前节点键,值
		 */
		private List<Node<I, V>> nodes;

		/**
		 * 子节点
		 */
		private List<TreeNode<I, V>> childes;

		public List<Node<I, V>> getNodes() {
			return nodes;
		}

		public void setNodes(List<Node<I, V>> nodes) {
			this.nodes = nodes;
		}

		public List<TreeNode<I, V>> getChildes() {
			return childes;
		}

		public void setChildes(List<TreeNode<I, V>> childes) {
			this.childes = childes;
		}
	}

	public void put(Node<I, V> node) {
		List<Node<I, V>> rootNodes = this.rootNode.getNodes();
		if (rootNodes == null) {
			rootNodes = new ArrayList<>();
		}

	}

	public void insert() {
	}


}
