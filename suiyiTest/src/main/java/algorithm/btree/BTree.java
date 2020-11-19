package algorithm.btree;

import java.util.List;

/**
 * @author LinYuanSheng
 * @date 2020/11/19
 */
public class BTree {
	public BTree(int M) {
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

}
