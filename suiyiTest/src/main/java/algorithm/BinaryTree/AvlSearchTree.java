package algorithm.BinaryTree;

/**
 * Created by lys on 2019/2/26.
 * Avl 平衡树
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class AvlSearchTree<AnyType extends Comparable<? super AnyType>> {
	private static final int ALLOW_IMBALANCE = 1;

	public static class AvlNode<AnyType> {
		//constructors
		AvlNode(AnyType thElement) {
			this(thElement, null, null);
		}

		AvlNode(AnyType thElement, AvlNode<AnyType> left, AvlNode<AnyType> right) {
			this.element = thElement;
			this.left = left;
			this.right = right;
		}

		AnyType element;                    // The data in the node
		AvlNode<AnyType> left;                // Left child
		AvlNode<AnyType> right;                // Right child
		int height;                            //Height
	}


	private AvlNode<AnyType> root;

	/**
	 * Return the height of node t, or -1, if null.
	 */
	private int height(AvlNode<AnyType> t) {
		return t == null ? -1 : t.height;
	}
	public void insert(AnyType t){
		root = insert(t,root);
	}
	/**
	 * Internal method to insert into a subtree
	 *
	 * @param x the item to insert
	 * @param t the node that roots the subtree
	 * @return the new root of the subtree
	 */
	private AvlNode<AnyType> insert(AnyType x, AvlNode<AnyType> t) {
		System.out.println("插入");
		if (t == null) {
			return new AvlNode<AnyType>(x, null, null);
		}
		int compareResult = x.compareTo(t.element);
		if (compareResult < 0) {
			t.left = insert(x, t.left);
		} else if (compareResult > 0) {
			t.right = insert(x, t.right);
		}

		return balance(t);
	}

	//Assume t is either balanced or within one of being balanced
	private AvlNode<AnyType> balance(AvlNode<AnyType> t) {
		System.out.println("翻转");
		if (t == null) {
			return t;
		}
		if (height(t.left) - height(t.right) > ALLOW_IMBALANCE) {
			if (height(t.left.left) >= height(t.left.right)) {
				t = rotateWithLeftChild(t);
			} else {
				t = doubleWithLeftChild(t);
			}
		} else if (height(t.right) - height(t.left) > ALLOW_IMBALANCE) {
			if (height(t.right.right) >= height(t.right.left)) {
				t = rotateWithRightChild(t);
			} else {
				t = doubleWithRightChild(t);
			}
		}
		t.height = Math.max(height(t.left), height(t.right)) + 1;
		return t;
	}

	/**
	 * Rotate binary tree node with left child
	 * For AVL trees, this is a  single rotation for case 1.
	 * Update heights, then return new root.
	 *
	 *       k2               k1
	 * 	 k1     z  --->     x      k2
	 * x	y                   y    z
	 * @param k2 the  node that roots the subtree
	 * @return new root
	 */
	private AvlNode<AnyType> rotateWithLeftChild(AvlNode<AnyType> k2) {
		AvlNode<AnyType> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max(height(k1.left), k2.height) + 1;
		return k1;
	}

	/**
	 * Double rotate binary tree node: first left child
	 * with its right child; the node k3 with new left child.
	 * For AVL trees, this is a double rotation for case 2.
	 * Update heights, then return new root.
	 *       k3                      k2
	 *    k1     d				 k1	      k3
	 * a     k2      ----->   a     b    c    d
	 * 	   b    c
	 * @param k3 the node that roots the subtrees
	 * @return new root
	 */
	private AvlNode<AnyType> doubleWithLeftChild(AvlNode<AnyType> k3) {
		k3.left = rotateWithRightChild(k3.left);
		return rotateWithLeftChild(k3);
	}

	/**
	 *  Rotate binary tree node with right child
	 * For AVL trees, this is a  single rotation for case 1.
	 * Update heights, then return new root.
	 *        k1                     k2
	 *     z      k2    ----->   k1      y
	 *          x    y          z   x
	 * @param k1
	 * @return
	 */
	private AvlNode<AnyType> rotateWithRightChild(AvlNode<AnyType> k1) {
		AvlNode<AnyType> k2 = k1.right;
		k1.right =  k2.left;
		k2.left = k1;
		k1.height = Math.max(height(k1.left), height(k1.right)) +1;
		k2.height = Math.max(height(k2.right), k1.height) +1;
		return k2;
	}

	/**
	 * doubleWithLeftChild  反一哈
	 * 		 k3				     k2
	 *    d     k1            k3    k1
	 *    	  k2   a  ----> d   b   c   a
	 *    	b  c
	 * @param k3
	 * @return
	 */
	private AvlNode<AnyType> doubleWithRightChild(AvlNode<AnyType> k3) {
		k3.right = rotateWithLeftChild(k3.right);
		return rotateWithRightChild(k3);
	}

	private AvlNode<AnyType> remove(AnyType x, AvlNode<AnyType> t){
		if (t == null){
			return t;
		}
		int compareResult = x.compareTo(t.element);

		if (compareResult < 0){
			t.left = remove(x, t.left);
		} else if (compareResult > 0){
			t.right = remove(x, t.right);
		} else if (t.left !=null && t.right !=null){
			t.element = findMin(t.right).element;
			t.right = remove(t.element, t.right);
		} else {
			t = (t.left != null) ? t.left : t.right;
		}

		return balance(t);
	}

	private AvlNode<AnyType> findMin(AvlNode<AnyType> t) {
		if (t == null){
			return t;
		}
		if( t.left == null){
			return t;
		}
		return findMin(t);
	}

}
 class AvlSearchTreeTest{
	 public static void main(String[] args) {
		 AvlSearchTree avlSearchTree = new AvlSearchTree<Integer>();
		 avlSearchTree.insert(5);
		 System.out.println("--");
		 avlSearchTree.insert(4);
		 System.out.println("--");
		 avlSearchTree.insert(3);

	 }

}
