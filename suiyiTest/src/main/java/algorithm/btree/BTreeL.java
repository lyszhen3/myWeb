package algorithm.btree;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
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
	private int MIN_KEYS = MID - 1;

	class Node<V> implements Comparable<Node<V>> {

		private TreeNode leftChild;

		private TreeNode rightChild;

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

		/**
		 * 父节点
		 */
		private TreeNode parentTreeNode;
		/**
		 * 高度
		 */
		private int height;

		/**
		 * 当前节点键,值
		 */
		private TreeSet<Node<V>> values;

		/**
		 * 子节点
		 */
		private List<TreeNode> childes;

		@Override
		public String toString() {
			return "TreeNode{" +
					"nodes=" + values +
					'}';
		}

		TreeNode() {
			values = new TreeSet<>();
			childes = new ArrayList<>();
		}

		TreeNode(TreeSet<Node<V>> inNodes) {
			values = new TreeSet<>();
			childes = new ArrayList<>();
			values.addAll(inNodes);
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public TreeSet<Node<V>> getValues() {
			return values;
		}

		public void setValues(TreeSet<Node<V>> values) {
			this.values = values;
		}

		public List<TreeNode> getChildes() {
			return childes;
		}

		public TreeNode lastChild() {
			if (childes == null || childes.isEmpty()) {
				return null;
			}
			return childes.get(childes.size() - 1);
		}

		public TreeNode firstChild() {
			if (childes == null || childes.isEmpty()) {
				return null;
			}
			return childes.get(0);
		}

		public void setChildes(List<TreeNode> childes) {
			this.childes = childes;
		}

	}

	/**
	 * 假设 m = 5
	 * <p>
	 * 这里索引和value先一样
	 */
	public void insert(V v) {
		//金字塔总是从下往上垒
		//如果叶子节点》=m ，则分叉
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
	 *
	 * @param v 1.如果是非叶子节点
	 *          1.1 从左边子节点最右边的键替换，如果有子节点继续往下搜索替换，然后删除该子节点键值
	 *          <p>
	 *          2.如果是子叶节点
	 *          2.1 直接删除该key，如果键个数不满足 ceil(M/2) -1 ,如果左右兄弟键值数量皆大于ceil(M/2)-1 ,则优先从左兄弟拿key，这里的操作是父节点一个key 下移，左兄弟最右边key上移倒父节点.
	 *          否则把右兄弟最左的key上移
	 *          2.2 如果兄弟节点键值个数均<=ceil(M/2) -1 则直接从父节点拿，此时该节点会合并，优先向左兄弟合并。因此导致父节点键值个数少于ceil(M/2)-1.则递归向上操作
	 */
	public void delete(V v) {

		Integer index = (Integer) v;
		deleteByIndex(index);

	}

	private void deleteByIndex(Integer index) {

		//寻找节点
		TreeNode treeNode = findTreeNode(index, rootNode);

		if (treeNode == null) {
			//如果没找到，就不用删了
			return;
		}

		TreeNode currentTree = treeNode;
		//如果有相同的node 这里取第一个吧
		final Node<V> vNode = treeNode.getValues().stream().filter(n -> n.getIndex().intValue() == index).findFirst().orElse(null);
		Node<V> deleteNode = vNode;
		if (!isLeaf(currentTree)) {
			//如果不是叶子节点
			//寻找小于index的最大叶子节点，作为替换的node
			//先找最大的左子tree
			TreeNode leftChildMaxTree = findLeftMaxTreeByParent(vNode, treeNode);
			//再找左子tree的最大叶子节点
			TreeNode maxRightLeafTree = findRightMaxLeafTree(leftChildMaxTree);

			final Node<V> last = maxRightLeafTree.getValues().last();
			//删除查找到的元素
//			currentTree.getNodes().remove(vNode);
			//添加最大右叶子节点
			currentTree.getValues().add(last);
			maxRightLeafTree.getValues().remove(last);
			maxRightLeafTree.getValues().add(deleteNode);
			currentTree = maxRightLeafTree;
		}
		currentTree.getValues().remove(deleteNode);

		rotateForBalance(currentTree);

	}

	private void rotateForBalance(TreeNode currentTree) {
		if (currentTree.getValues().size() >= MIN_KEYS) {
			//如果当前节点删除了一个还是符合最小键个数，则直接删除,如果是根节点也请直接删除吧。。
			return;
		}
		if (currentTree.getHeight() == 0) {
			if (currentTree.getValues().isEmpty()) {
				//如果当前节点是根目录，且元素被删光了，就把当前节点移除
				decreaseHeight(currentTree.getChildes());
				//按理说只有一个子节点
				rootNode = currentTree.getChildes().get(0);
			}
			return;
		}
		if (currentTree.getValues().size() < MIN_KEYS) {
			//这个时候要看兄弟节点满足不
			TreeNode leftBrother = findLeftBrotherTreeNode(currentTree);

			TreeNode rightBrother = findRightBrotherTreeNode(currentTree);
			if (leftBrother != null && leftBrother.getValues().size() >= MIN_KEYS + 1) {
				//如果左兄第满足则直接从左兄第取最右边的NODE 上移
				final TreeNode parentTreeNode = currentTree.parentTreeNode;
				//下移的node
				Node<V> downNode = findParentLeftNode(currentTree, parentTreeNode);
				final TreeNode leftChild = downNode.leftChild;
				//最后一个上移
				final Node<V> last = leftChild.getValues().last();
				parentTreeNode.getValues().remove(downNode);
				parentTreeNode.getValues().add(last);
				currentTree.getValues().add(downNode);
				downNode.leftChild = last.rightChild;
				downNode.rightChild = currentTree.firstChild();
				last.leftChild = leftChild;
				last.rightChild = currentTree;
				return;
			}
			if (rightBrother != null && rightBrother.getValues().size() >= MIN_KEYS + 1) {
				//如果右兄弟满足则直接从有兄弟取最左边NODE 上移
				final TreeNode parentTreeNode = currentTree.parentTreeNode;
				//下移的node
				Node<V> downNode = findParentRightNode(currentTree, parentTreeNode);
				final TreeNode rightChild = downNode.rightChild;
				//最后一个上移
				final Node<V> first = rightChild.getValues().first();
				parentTreeNode.getValues().remove(downNode);
				parentTreeNode.getValues().add(first);
				currentTree.getValues().add(downNode);
				downNode.leftChild = currentTree.lastChild();
				downNode.rightChild = first.leftChild;
				first.leftChild = currentTree;
				first.rightChild = rightChild;
				return;
			}
			//如果左右节点均不满足，则直接从父节点拿一个下来(优先拿左边的，这里左边可能没有，也可能右边没有。要特殊考虑)，并合并左右子节点
			final TreeNode parentTreeNode = currentTree.parentTreeNode;
			final Node<V> downNode = findParentLeftNode(currentTree, parentTreeNode);
			final Node<V> rightDownNode = findParentRightNode(currentTree, parentTreeNode);
			if (downNode != null) {
				downNode.rightChild = currentTree.firstChild();
				downNode.leftChild = leftBrother.lastChild();
				currentTree.getValues().add(downNode);
				//合并操作, 这里会不会超出去? 兄弟节点值个数<=MIN_KEYS,当前节点值个数<MIN_KEYS 合并后个数<=2*(MID -1) + 1【父节点拿的】 = M
				currentTree.getValues().addAll(leftBrother.getValues());
				leftBrother.getChildes().addAll(currentTree.getChildes());
				currentTree.setChildes(leftBrother.getChildes());
				parentTreeNode.getValues().remove(downNode);
				//删除被合并的
				parentTreeNode.getChildes().remove(leftBrother);

			} else if (rightDownNode != null) {
				rightDownNode.rightChild = rightBrother.firstChild();
				rightDownNode.leftChild = currentTree.lastChild();
				currentTree.getValues().add(rightDownNode);
				//合并操作
				currentTree.getValues().addAll(rightBrother.getValues());
				currentTree.getChildes().addAll(rightBrother.getChildes());
				parentTreeNode.getValues().remove(rightDownNode);
				//删除被合并右子节点
				parentTreeNode.getChildes().remove(rightBrother);

			}

			rotateForBalance(parentTreeNode);
		}
	}

	private Node<V> findParentRightNode(TreeNode currentTree, TreeNode parentTreeNode) {
		if (currentTree == null || parentTreeNode == null) {
			return null;
		}
		for (Node<V> node : parentTreeNode.getValues()) {
			if (node.leftChild == currentTree) {
				return node;
			}
		}
		return null;
	}

	private Node<V> findParentLeftNode(TreeNode currentTree, TreeNode parentTreeNode) {
		if (currentTree == null || parentTreeNode == null) {
			return null;
		}
		for (Node<V> node : parentTreeNode.getValues()) {
			if (node.rightChild == currentTree) {
				return node;
			}
		}
		return null;
	}

	private TreeNode findRightBrotherTreeNode(TreeNode currentTree) {
		final TreeNode parentTreeNode = currentTree.parentTreeNode;
		if (parentTreeNode == null) {
			return null;
		}
		final List<TreeNode> childes = parentTreeNode.getChildes();
		//这里也不用什么二分查找啦，随便写写
		for (int i = 0; i < childes.size(); i++) {
			if (childes.get(i) == currentTree && (i + 1) <= childes.size() - 1) {
				return childes.get(i + 1);
			}
		}
		return null;
	}

	private TreeNode findLeftBrotherTreeNode(TreeNode currentTree) {
		final TreeNode parentTreeNode = currentTree.parentTreeNode;
		if (parentTreeNode == null) {
			return null;
		}
		final List<TreeNode> childes = parentTreeNode.getChildes();
		//这里也不用什么二分查找啦，随便写写
		for (int i = 0; i < childes.size(); i++) {
			if (childes.get(i) == currentTree && (i - 1) >= 0) {
				return childes.get(i - 1);
			}
		}
		return null;
	}

	private TreeNode findRightMaxLeafTree(TreeNode leftChildMaxTree) {

		if (leftChildMaxTree.getChildes() == null || leftChildMaxTree.getChildes().isEmpty()) {
			return leftChildMaxTree;
		}
		return findRightMaxLeafTree(leftChildMaxTree.getChildes().get(leftChildMaxTree.getChildes().size() - 1));
	}

	private TreeNode findLeftMaxTreeByParent(Node<V> vNode, TreeNode treeNode) {

		//这里要倒着找，🤭
		for (int i = treeNode.getChildes().size() - 1; i >= 0; i--) {
			final TreeNode childNode = treeNode.getChildes().get(i);
			final Node<V> last = childNode.getValues().last();
			if (last.getIndex() <= vNode.getIndex()) {
				//倒叙找到第一个最后一个元素小于寻找index的
				return childNode;
			}
		}
		return null;
	}

	/**
	 * 如果是叶子节点
	 *
	 * @param treeNode
	 * @return
	 */
	private boolean isLeaf(TreeNode treeNode) {
		if (treeNode == null) {
			return false;
		}
		return treeNode.getChildes() == null || treeNode.getChildes().size() <= 0;
	}

	private TreeNode findTreeNode(Integer index, TreeNode treeNode) {

		if (treeNode == null) {
			return null;
		}
		final TreeSet<Node<V>> nodes = treeNode.getValues();

		final Iterator<Node<V>> iterator = nodes.iterator();
		while (iterator.hasNext()) {
			Node<V> node = iterator.next();
			if (node.getIndex() == index.intValue()) {
				//如果索引相等，则返回当前节点
				return treeNode;
			}
			if (node.getIndex() < index && iterator.hasNext()) {
				continue;
			}
			if (node.getIndex() < index && !iterator.hasNext()) {
				return findTreeNode(index, node.rightChild);
			}
			if (node.getIndex() > index) {
				//如果大于搜索的索引，向下找子节点，该子节点键[min]<=index<=键[max]
				return findTreeNode(index, node.leftChild);

			}
		}

		return null;
	}

	private void insertNode(TreeNode treeNode, Node<V> node) {
		final List<TreeNode> childes = treeNode.getChildes();

		//寻找往哪个子节点插入node
		if (childes != null && !childes.isEmpty()) {
			for (int i = 0; i < childes.size(); i++) {
				final TreeNode childTreeNodeN = childes.get(i);
				//是否大于后一个节点头部，如果大于则下一个节点，如果小于则当前节点
				final Node<V> last = childTreeNodeN.getValues().last();
				if (node.getIndex() <= last.getIndex() || (i == childes.size() - 1 && node.getIndex() > last.getIndex())) {
					//如果大于插入当前节点
					insertNode(childes.get(i), node);
					return;
				}

			}

		}
		//如果没有子节点，则插入当前节点
		treeNode.getValues().add(node);
		balance(treeNode);

	}

	private void balance(TreeNode treeNode) {
		//这里如果一个节点大于等于m个元素，则将该节点分叉出两个节点，将中间的元素上移到父节点
		//分叉递归
		//当前高度
		final int height = treeNode.getHeight();
		if (treeNode.getValues().size() >= M) {
			final TreeSet<Node<V>> leftValues = treeNode.getValues().stream().limit(MID - 1).collect(Collectors.toCollection(TreeSet::new));

			final TreeNode leftTree = new TreeNode(leftValues);
			//这里高度假设上层没有增加一个节点
			leftTree.setHeight(height);
			final TreeSet<Node<V>> rightValues = treeNode.getValues().stream().skip(MID).collect(Collectors.toCollection(TreeSet::new));
			final TreeNode rightTree = new TreeNode(rightValues);
			//这里高度假设上层没有增加一个节点
			rightTree.setHeight(height);
			final Node<V> vNode = treeNode.getValues().stream().skip(MID - 1).findFirst().get();

			//把当前节点的子节点做分割给leftTree 和 rightTree
			//子节点最后一个元素小于等于vNode的index的分给leftTree作为子节点，否则给rightTree作为子节点
			if (treeNode.getChildes() != null && !treeNode.getChildes().isEmpty()) {
				final List<TreeNode> childes = treeNode.getChildes();
				final List<TreeNode> leftTreeChild = childes.stream().filter(tree -> tree.getValues().last().getIndex() <= vNode.getIndex()).toList();
				final List<TreeNode> rightTreeChild = childes.stream().filter(tree -> tree.getValues().first().getIndex() > vNode.getIndex()).toList();
				leftTree.getChildes().addAll(leftTreeChild);
				rightTree.getChildes().addAll(rightTreeChild);
				leftTreeChild.forEach(t -> t.parentTreeNode = leftTree);
				rightTreeChild.forEach(t -> t.parentTreeNode = rightTree);
			}
			//根据高度向上寻找父节点并把当前节点上移倒父节点
			TreeNode upFloor = findUpFloor(vNode, height - 1);

			if (upFloor == null) {
				//如果父节点为null,则新建一个节点，把左右节点设为子节点
				upFloor = new TreeNode();
				upFloor.setHeight(height);
				upFloor.getValues().add(vNode);
				upFloor.getChildes().add(leftTree);
				upFloor.getChildes().add(rightTree);
				leftTree.parentTreeNode = upFloor;
				rightTree.parentTreeNode = upFloor;
				vNode.leftChild = leftTree;
				vNode.rightChild = rightTree;
				this.rootNode = upFloor;
				//高度+1
				increaseHeight(rootNode.getChildes());

			} else {
				upFloor.getValues().add(vNode);
				//移除当前被拆分为两个分支的节点
				upFloor.getChildes().remove(treeNode);
				upFloor.getChildes().add(leftTree);
				upFloor.getChildes().add(rightTree);
				leftTree.parentTreeNode = upFloor;
				rightTree.parentTreeNode = upFloor;
				vNode.leftChild = leftTree;
				vNode.rightChild = rightTree;
				balance(upFloor);
			}

		}
	}

	private void increaseHeight(List<TreeNode> childes) {
		if (childes == null || childes.isEmpty()) {
			return;
		}
		for (TreeNode childe : childes) {
			childe.setHeight(childe.getHeight() + 1);
			increaseHeight(childe.getChildes());
		}
	}

	private void decreaseHeight(List<TreeNode> childes) {
		if (childes == null || childes.size() == 0) {
			return;
		}
		for (TreeNode childe : childes) {
			childe.setHeight(childe.getHeight() - 1);
			decreaseHeight(childe.getChildes());
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
				if (childe.getValues().contains(childNode)) {
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

		bTreeL.delete(8);
		bTreeL.delete(7);
		//1

	}

}
