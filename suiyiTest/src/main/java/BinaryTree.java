import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by pc on 2017-10-13.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class BinaryTree {
    Node root;
    /**
     *         A
     *      B     C
     *   D   E   F  G
     * 实现二叉树的层级遍历
     */
    public class Node{
        int key;
        String value;
        Node left;
        Node right;
        public Node(int key,String value){
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }

    }

    public void createTreeDemo(){
        Node node1,node2,node3,node4,node5,node6,node7;
        node1 = new Node(1,"A");
        node2 = new Node(2,"B");
        node3 = new Node(3,"C");
        node4 = new Node(4,"D");
        node5 = new Node(5,"E");
        node6 = new Node(6,"F");
        node7 = new Node(7,"G");
        root = node1;
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;
    }

    /**
     * 前序遍历
     * @param node
     */
    public void preOrder(Node node){
        if(node != null){
            visit(node);
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    /**
     * 中序遍历
     * @param node
     */
    public void inOrder(Node node){
        if(node != null){
            inOrder(node.left);
            visit(node);
            inOrder(node.right);
        }
    }
    public void beOrder(Node node){
        if(node !=null){
            beOrder(node.left);
            beOrder(node.right);
            visit(node);
        }
    }
    public void visit(Node node){
        System.out.print(node.value);
    }

    public int[] floorSort(){
        createTreeDemo();



        return null;
    }

    /**
     * 借助链表实现层序遍历
     * @param root
     */
    public void levelIterator(Node root)
    {
        if(root == null)
        {
            return ;
        }
        LinkedList<Node> queue = new LinkedList<Node>();
        Node current = null;
        queue.offer(root);//将根节点入队
        while(!queue.isEmpty())
        {
            current = queue.poll();//出队队头元素并访问
            System.out.print(current.value +"-->");
            if(current.left != null)//如果当前节点的左节点不为空入队
            {
                queue.offer(current.left);
            }
            if(current.right != null)//如果当前节点的右节点不为空，把右节点入队
            {
                queue.offer(current.right);
            }
        }

    }

    public static void main(String[] args) {
        System.out.println("前序遍历");
        BinaryTree tree = new BinaryTree();
        tree.createTreeDemo();
        tree.preOrder(tree.root);
        System.out.println("\n中序遍历");
        tree.inOrder(tree.root);
        System.out.println("\n后续遍历");
        tree.beOrder(tree.root);
        System.out.println("\n层序遍历");
        tree.levelIterator(tree.root);
        LinkedList<Integer> linkedList = new LinkedList<>();
        Vector v = new Vector();

    }
}
