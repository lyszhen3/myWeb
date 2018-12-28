package BinaryTree;

/**
 * Created by lys on 2018/12/21.
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

	private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> root) {
		return null;
	}

	public void remove(AnyType x) {
		root = remove(x, root);
	}

	private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> root) {
		return null;
	}

	public void printTree() {

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
