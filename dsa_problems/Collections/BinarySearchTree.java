package dsa_problems.Collections;

class Node1 {
    int data;
    Node1 left;
    Node1 right;

    Node1(int data) {
        this.data = data;
        left = null;
        right = null;
    }
}

public class BinarySearchTree {

    Node1 root;

    public void insert(int data) {

        root = insertRec(root, data);

    }

    public Node1 insertRec(Node1 root, int data) {

        if(root == null) 
            return root = new Node1(data);
        
        if(data < root.data) 
            root.left = insertRec(root.left, data);
        
        else if(data > root.data)
            root.right = insertRec(root.right, data);

        return root;
    }

    public void inorder() {
        inorderRec(root);
    }
    public void preorder() {
        preorderRec(root);
    }
    public void postorder() {
        postorderRec(root);
    }

    public void inorderRec(Node1 root){
        if(root != null) {
            inorderRec(root.left);
            System.out.print(root.data + " ");
            inorderRec(root.right);
        }
    }

    public void preorderRec(Node1 root) {
        if(root != null) {
            System.out.print(root.data + " ");
            inorderRec(root.left);
            inorderRec(root.right);
        }
    }

    public void postorderRec(Node1 root) {
        if(root != null) {
            inorderRec(root.left);
            inorderRec(root.right);
            System.out.print(root.data + " ");
        }
    }
}
