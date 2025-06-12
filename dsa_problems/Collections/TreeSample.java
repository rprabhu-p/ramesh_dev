package dsa_problems.Collections;

public class TreeSample {

    public static void main(String[] args) {

        BinarySearchTree tree = new BinarySearchTree();

        tree.insert(8);
        tree.insert(7);
        tree.insert(12);
        tree.insert(15);
        tree.insert(2);
        tree.insert(5);

        System.out.print("InORder : ");
        tree.inorder();
        System.out.println();

        System.out.print("PreOrder : ");
        tree.preorder();
        System.out.println();
        
        System.out.print("PostOrder : ");
        tree.postorder();
        System.out.println();
        
    }
    
}
