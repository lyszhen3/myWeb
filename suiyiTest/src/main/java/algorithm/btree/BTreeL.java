package algorithm.btree;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author LinYuanSheng
 * @date 2020/11/19
 * 有1<非子结点(根节点外)<=M
 * ceil(M/2)-1<=键值数<=M-1
 */
public class BTreeL<V> {

	private TreeNode rootNode;

	public TreeNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(TreeNode rootNode) {
		this.rootNode = rootNode;
	}

	public BTreeL(int M) {
		this.M = M;
		this.rootNode = new TreeNode();
		this.rootNode.height = 0;
	}

	private int M = 3;

	private int MID = (M + 2 - 1) / 2;

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
		public String toString() {
			return "Node{" +
					"index=" + index +
					", value=" + value +
					'}';
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

	/**
	 *
	 */

	class TreeNode {

		//高度
		private int height;

		/**
		 * 当前节点键,值
		 */
		private TreeSet<Node<V>> nodes;

		/**
		 * 子节点
		 */
		private List<TreeNode> childes;

		@Override
		public String toString() {
			return "TreeNode{" +
					"nodes=" + nodes +
					'}';
		}

		TreeNode() {
			nodes = new TreeSet<>();
			childes = new ArrayList<>();
		}

		TreeNode(TreeSet<Node<V>> inNodes) {
			nodes = new TreeSet<>();
			childes = new ArrayList<>();
			nodes.addAll(inNodes);
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
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

	/**
	 * 删除
	 * @param v
	 */
	public void delete(V v) {


	}

	private void insertNode(TreeNode treeNode, Node<V> node) {
		final List<TreeNode> childes = treeNode.getChildes();

		if (childes != null && childes.size() > 0) {
			for (int i = 0; i < childes.size(); i++) {
				final TreeNode childTreeNodeN = childes.get(i);
				//是否大于后一个节点头部，如果大于则下一个节点，如果小于则当前节点
				final Node<V> last = childTreeNodeN.getNodes().last();
				if (node.getIndex() <= last.getIndex() || (i == childes.size() - 1 && node.getIndex() > last.getIndex())) {
					//如果大于插入当前节点
					insertNode(childes.get(i), node);
					return;
				}

			}

		}
		//如果没有子节点，则插入当前节点
		treeNode.getNodes().add(node);
		balance(treeNode);

	}

	public void balance(TreeNode treeNode) {
		//这里如果一个节点大于等于m个元素，则将该节点分叉出两个节点，将中间的元素上移到父节点
		//分叉递归
		//当前高度
		final int height = treeNode.getHeight();
		if (treeNode.getNodes().size() >= M) {
			final TreeSet<Node<V>> leftChild = treeNode.getNodes().stream().limit(MID - 1).collect(Collectors.toCollection(TreeSet::new));

			final TreeNode leftTree = new TreeNode(leftChild);
			//这里高度假设上层没有增加一个节点
			leftTree.setHeight(height);
			final TreeSet<Node<V>> rightChild = treeNode.getNodes().stream().skip(MID).collect(Collectors.toCollection(TreeSet::new));
			final TreeNode rightTree = new TreeNode(rightChild);
			//这里高度假设上层没有增加一个节点
			rightTree.setHeight(height);
			final Node<V> vNode = treeNode.getNodes().stream().skip(MID - 1).findFirst().get();

			//把当前节点的子节点做分割给leftTree 和 rightTree
			//子节点最后一个元素小于等于vNode的index的分给leftTree作为子节点，否则给rightTree作为子节点
			if (treeNode.getChildes() != null && treeNode.getChildes().size() > 0) {
				final List<TreeNode> childes = treeNode.getChildes();
				final List<TreeNode> leftTreeChild = childes.stream().filter(tree -> tree.getNodes().last().getIndex() <= vNode.getIndex()).collect(Collectors.toList());
				final List<TreeNode> rightTreeChild = childes.stream().filter(tree -> tree.getNodes().first().getIndex() > vNode.getIndex()).collect(Collectors.toList());
				leftTree.getChildes().addAll(leftTreeChild);
				rightTree.getChildes().addAll(rightTreeChild);
			}
			//根据高度向上寻找父节点并把当前节点上移倒父节点
			TreeNode upFloor = findUpFloor(vNode, height - 1);

			if (upFloor == null) {
				//如果父节点为null,则新建一个节点，把左右节点设为子节点
				upFloor = new TreeNode();
				upFloor.setHeight(height);
				upFloor.getNodes().add(vNode);
				upFloor.getChildes().add(leftTree);
				upFloor.getChildes().add(rightTree);
				this.rootNode = upFloor;
				//高度调整
				adjHeight(rootNode.getChildes());

			} else {
				upFloor.getNodes().add(vNode);
				//移除当前被拆分为两个分支的节点
				upFloor.getChildes().remove(treeNode);
				upFloor.getChildes().add(leftTree);
				upFloor.getChildes().add(rightTree);
				balance(upFloor);
			}

		}
	}

	private void adjHeight(List<TreeNode> childes) {
		if (childes == null || childes.size() == 0) {
			return;
		}
		for (TreeNode childe : childes) {
			childe.setHeight(childe.getHeight() + 1);
			adjHeight(childe.getChildes());
		}
	}

	private TreeNode findUpFloor(Node<V> vNode, int height) {
		if (height < 0) {
			return null;
		}
		if (height == 0) {
			return this.rootNode;
		}
		//这里要递归找
		return findNode(vNode, height, this.rootNode);
	}

	private TreeNode findNode(Node<V> childNode, int height, TreeNode treeNode) {
		if (treeNode.getHeight() == height) {
			//如果高度符合
			//校验子节点是否包含该元素
			for (TreeNode childe : treeNode.getChildes()) {
				if (childe.getNodes().contains(childNode)) {
					return childe;
				}
			}

		}
		//如果高度不符合，向下找
		for (TreeNode childe : treeNode.getChildes()) {
			return findNode(childNode, height, childe);
		}
		return null;
	}

	public static void main(String[] args) {
		BTreeL<Integer> bTreeL = new BTreeL<>(3);
		bTreeL.insert(1);
		bTreeL.insert(2);
		bTreeL.insert(3);
		bTreeL.insert(4);
		bTreeL.insert(5);
		bTreeL.insert(6);
		bTreeL.insert(7);
		bTreeL.insert(8);
		//1

	}

}
