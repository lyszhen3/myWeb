package algorithm.btree;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.TreeSet;

/**
 * @author LinYuanSheng
 * @date 2020/11/19
 * 有1<非子结点(根节点外)<=M
 * ceil(M/2)-1<=键值数<=M-1
 */
public class BTreeL<I, V> {

	private TreeNode rootNode;

	public TreeNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(TreeNode rootNode) {
		this.rootNode = rootNode;
	}

	public BTreeL(int M) {
		this.M = M;
	}

	private int M = 3;

	private int minNodes = M / 2;

	private int maxNodes = M - 1;

	class Node<V> implements Comparable<Node<V>> {
		//索引
		private Integer index;
		//值
		private V value;

		public Integer getIndex() {
			return index;
		}

		public void setIndex(Integer index) {
			this.index = index;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public int compareTo(Node<V> o) {
			if (o != null) {
				final Integer index = o.getIndex();
				if (index != null) {
					//这里用反射试试
					try {
						final Method compareTo = Integer.class.getMethod("compareTo", Integer.class);
						final Object invoke = compareTo.invoke(this.index, index);
						return (int) invoke;
					} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignore) {
					}

				}
			}

			return 0;
		}
	}

	class TreeNode {

		/**
		 * 当前节点键,值
		 */
		private TreeSet<Node<V>> nodes;

		/**
		 * 子节点
		 */
		private List<TreeNode> childes;

		public TreeSet<Node<V>> getNodes() {
			return nodes;
		}

		public void setNodes(TreeSet<Node< V>> nodes) {
			this.nodes = nodes;
		}

		public List<TreeNode> getChildes() {
			return childes;
		}

		public void setChildes(List<TreeNode> childes) {
			this.childes = childes;
		}
	}

	/**
	 * m = 5
	 * <p>
	 * 这里索引和value先一样
	 */
	public void insert(V v) {
		//金字塔总是从下往上垒
		//如果叶子节点》=5 ，则分叉
		//1.要从root开始找
		//2.要一直往下寻找插入到叶子节点
		final Node<V> node = new Node<>();
		//先直接Integer
		node.setIndex((Integer) v);
		node.setValue(v);
		insertNode(rootNode, node);
	}

	public void insertNode(TreeNode treeNode, Node<V> node) {
		final List<TreeNode> childes = treeNode.getChildes();

		if (childes != null && childes.size() > 0) {
			for (int i = 0; i < childes.size(); i++) {
				final TreeNode childTreeNodeN = childes.get(i +1);
				//是否大于后一个节点头部，如果大于则下一个节点，如果小于则当前节点
				final Node<V> first = childTreeNodeN.getNodes().first();
				if (first.getIndex()> node.getIndex()) {
					//如果大于插入当前节点
					insertNode(childes.get(i), node);
				}
			}

		}
		//如果没有子节点，则插入当前节点
		treeNode.getNodes().add(node);
		//这里如果一个节点大于等于m个元素，则分叉

	}

}
