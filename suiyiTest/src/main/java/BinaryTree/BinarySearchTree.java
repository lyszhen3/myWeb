package BinaryTree;

/**
 * Created by lys on 2018/12/21.
 * 二叉树查找
 * 性质: 左子树中所有项的值都小于x中的项, 右子树中所有项的值都大于x中的项
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
	public static class BinaryNode<AnyType> {
		BinaryNode(AnyType theElement) {
			this(theElement, null, null);
		}

		BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt) {
			this.element = theElement;
			this.left = lt;
			this.right = rt;
		}

		AnyType element;
		BinaryNode<AnyType> left;
		BinaryNode<AnyType> right;

	}

	private BinaryNode<AnyType> root;

	public BinarySearchTree() {
		this.root = null;
	}

	public void makeEmpty() {
		this.root = null;
	}

	public boolean isEmpty() {
		return this.root == null;
	}

	public boolean contains(AnyType x) {
		return contains(x, root);
	}

	public AnyType findMin() {
		if (isEmpty()) {
			throw new NullPointerException();
		}
		return findMin(this.root).element;
	}

	public AnyType findMax() {
		if (isEmpty()) {
			throw new NullPointerException();
		}
		return findMax(this.root).element;
	}


	public void insert(AnyType x) {
		root = insert(x, root);
	}
	private int height( BinaryNode<AnyType> t){
		if (t == null){
			return -1;
		} else {
			return Math.max(height(t.right), height(t.left))+1;
		}
	}
	/**
	 * Internal method to insert into a subtree,
	 *
	 * @param x    the item to insert.
	 * @param root the node that roots the subtree.
	 * @return the new root of subtree.
	 */
	private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> root) {
		if (root == null) {
			return new BinaryNode<>(x, null, null);
		}
		int compareResult = x.compareTo(root.element);
		if (compareResult < 0) {
			root.left = insert(x, root.left);
		} else if (compareResult > 0) {
			root.right = insert(x, root.right);
		}
		return root;
	}

	public void remove(AnyType x) {
		root = remove(x, root);
	}

	private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> root) {
		if (root == null) {
			return root;
		}
		int compareResult = x.compareTo(root.element);
		if (compareResult < 0) {
			root.left = remove(x, root.left);
		} else if (compareResult > 0) {
			root.right = remove(x, root.right);
		} else if (root.left != null && root.right != null) {
			//这里遍历了两次 如何改成一次? 替换的同时又给删了
//			root.element = findMin(root.right).element;
//			root.right = remove(root.element, root.right);
			root.right = removeMin(root,root.right);
		} else {
			root = (root.left != null) ? root.left : root.right;
		}
		return root;
	}

	private BinaryNode<AnyType> removeMin(BinaryNode<AnyType> n, BinaryNode<AnyType> t) {
		if (t == null) {
			return t;
		}
		if (t.left == null) {
			n.element = t.element;
			t = t.right;
		} else {
			t.left = removeMin(n,t.left);
		}

		return t;
	}

	public void printTree() {
		if (isEmpty()){
			System.out.println("empty tree");
		} else {
			printTree(root);
		}
	}

	/**
	 * 中序遍历
	 * @param t
	 */
	private void printTree(BinaryNode<AnyType> t) {
		if (t !=null){
			printTree(t.left);
			System.out.println(t.element);
			printTree(t.right);
		}
	}


	private BinaryNode<AnyType> findMax(BinaryNode<AnyType> root) {
		if (root != null) {
			while (root.right != null) {
				root = root.right;
			}
		}
		return root;
	}

	private BinaryNode<AnyType> findMin(BinaryNode<AnyType> root) {
		if (root == null) {
			return null;
		} else if (root.left == null) {
			return root;
		}
		return findMin(root.left);
	}


	private boolean contains(AnyType x, BinaryNode<AnyType> root) {

		if (root == null) {
			return false;
		}
		int compareResult = x.compareTo(root.element);
		if (compareResult < 0) {
			return contains(x, root.left);
		} else if (compareResult > 0) {
			return contains(x, root.right);
		} else {
			return true;
		}
	}

}

class SearchTest {
	public static void main(String[] args) {
		BinarySearchTree<Integer> integerBinarySearchTree = new BinarySearchTree<Integer>();
		integerBinarySearchTree.insert(6);
		integerBinarySearchTree.insert(2);
		integerBinarySearchTree.insert(8);
		integerBinarySearchTree.insert(1);
		integerBinarySearchTree.insert(5);
		integerBinarySearchTree.insert(3);
		integerBinarySearchTree.insert(4);
		integerBinarySearchTree.remove(2);
		Integer max = integerBinarySearchTree.findMax();
		System.out.println("max:" + max);
		System.out.println("min:" + integerBinarySearchTree.findMin());
	}
}
