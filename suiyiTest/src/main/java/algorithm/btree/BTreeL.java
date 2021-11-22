package algorithm.btree;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

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

	private int MID = (int) Math.ceil(M / 2);

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

		private TreeNode parentNode;
		/**
		 * 当前节点键,值
		 */
		private TreeSet<Node<V>> nodes;

		/**
		 * 子节点
		 */
		private List<TreeNode> childes;

		TreeNode(){
			nodes =  new TreeSet<>();
			childes = new ArrayList<>();
		}
		TreeNode(TreeSet<Node<V>> inNodes){
			nodes =  new TreeSet<>();
			childes = new ArrayList<>();
			nodes.addAll(inNodes);
		}

		public TreeSet<Node<V>> getNodes() {
			return nodes;
		}

		public void setNodes(TreeSet<Node<V>> nodes) {
			this.nodes = nodes;
		}

		public List<TreeNode> getChildes() {
			return childes;
		}

		public void setChildes(List<TreeNode> childes) {
			this.childes = childes;
		}

		public TreeNode getParentNode() {
			return parentNode;
		}

		public void setParentNode(TreeNode parentNode) {
			this.parentNode = parentNode;
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
				final TreeNode childTreeNodeN = childes.get(i);
				//是否大于后一个节点头部，如果大于则下一个节点，如果小于则当前节点
				final Node<V> last = childTreeNodeN.getNodes().last();
				if (node.getIndex() <= last.getIndex()) {
					//如果大于插入当前节点
					insertNode(childes.get(i), node);
				}
			}

		}
		//如果没有子节点，则插入当前节点
		treeNode.getNodes().add(node);
		balance(treeNode);

	}
	public void balance(TreeNode treeNode){
		//这里如果一个节点大于等于m个元素，则将该节点分叉出两个节点，将中间的元素上移到父节点
		//分叉递归
		if (treeNode.getNodes().size() >= M) {
			final Optional<Node<V>> first = treeNode.getNodes().stream().skip(MID - 1).findFirst();

			final TreeSet<Node<V>> leftChild = treeNode.getNodes().stream().limit(MID - 1).collect(Collectors.toCollection(TreeSet::new));

			final TreeNode leftTree = new TreeNode(leftChild);
			final TreeSet<Node<V>> rightChild = treeNode.getNodes().stream().skip(MID).collect(Collectors.toCollection(TreeSet::new));

			final TreeNode rightTree = new TreeNode(rightChild);
			final Node<V> vNode = first.get();
			//插入父节点，如果父节点为空，则构造一个
			TreeNode parentNode = treeNode.getParentNode();
			if(parentNode == null) {
				//如果父节点是空,则构造一个
				parentNode = new TreeNode();
				parentNode.getNodes().add(vNode);
				parentNode.getChildes().add(leftTree);
				parentNode.getChildes().add(rightTree);

			}else {
				parentNode.getNodes().add(vNode);
				parentNode.getChildes().add(leftTree);
				parentNode.getChildes().add(rightTree);
				balance(parentNode);
			}

		}
	}

	public static void main(String[] args) {

	}

}
